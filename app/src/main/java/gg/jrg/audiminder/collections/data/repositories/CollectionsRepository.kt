package gg.jrg.audiminder.collections.data.repositories

import android.content.SharedPreferences
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionCrossRefDTO
import gg.jrg.audiminder.collections.data.source.local.CollectionsLocalDataSource
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.collections.util.CollectionsSortingType
import gg.jrg.audiminder.core.data.asDomainModelList
import gg.jrg.audiminder.core.util.ImageService
import gg.jrg.audiminder.core.util.ScreenKey
import gg.jrg.audiminder.core.util.throwIfFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

interface CollectionsRepository {
    suspend fun refreshListOfCollections(forceUpdate: Boolean = false)
    suspend fun insertCollection(albumCollection: AlbumCollection): AlbumCollection
    suspend fun addAlbumToCollection(album: Album, albumCollection: AlbumCollection)
    suspend fun setSortingType(sortingType: CollectionsSortingType, screenKey: ScreenKey)
    fun getCollectionsWithAlbumsList(screenKey: ScreenKey): StateFlow<List<AlbumCollectionWithAlbums>>
    fun getStoredSortingType(screenKey: ScreenKey): CollectionsSortingType
    suspend fun deleteCollection(albumCollection: AlbumCollection)
}

class CollectionsRepositoryImpl @Inject constructor(
    private val collectionsLocalDataSource: CollectionsLocalDataSource,
    private val sharedPreferences: SharedPreferences,
    private val imageService: ImageService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CollectionsRepository {

    private val _collectionsWithAlbumsList =
        MutableStateFlow(emptyList<AlbumCollectionWithAlbums>())

    private val _sortedCollectionsWithAlbumsListsByScreen =
        mutableMapOf<ScreenKey, MutableStateFlow<List<AlbumCollectionWithAlbums>>>()

    override suspend fun refreshListOfCollections(forceUpdate: Boolean) {
        val latestUpdate = collectionsLocalDataSource.getLatestUpdate()
        val lastFetchTime = sharedPreferences.getLong("lastFetchTime", 0)

        if (forceUpdate || latestUpdate.isSuccess && latestUpdate.getOrNull()!! > lastFetchTime) {
            val listOfAllCollectionsResult = collectionsLocalDataSource.getCollectionsWithAlbums()

            if (listOfAllCollectionsResult.isSuccess) {
                _collectionsWithAlbumsList.value =
                    listOfAllCollectionsResult.getOrNull()!!.asDomainModelList()
                sharedPreferences.edit().putLong("lastFetchTime", System.currentTimeMillis())
                    .apply()
            } else {
                throw Exception(listOfAllCollectionsResult.exceptionOrNull())
            }
        }

        _sortedCollectionsWithAlbumsListsByScreen.forEach { (screenKey, sortedListStateFlow) ->
            val storedSortingType = getStoredSortingType(screenKey)
            val sortedList =
                sortCollectionsWithAlbumsList(storedSortingType, _collectionsWithAlbumsList.value)
            sortedListStateFlow.value = sortedList
        }
    }

    override suspend fun insertCollection(albumCollection: AlbumCollection): AlbumCollection {
        return collectionsLocalDataSource
            .insertAlbumCollection(albumCollection.asDatabaseModel())
            .getOrThrow()
            .asDomainModel()
    }

    override suspend fun addAlbumToCollection(album: Album, albumCollection: AlbumCollection) {
        val checkIfAlbumExistsInDatabaseResult =
            collectionsLocalDataSource
                .doesAlbumExistInDatabase(album.albumId)
                .getOrNull()!!

        if (!checkIfAlbumExistsInDatabaseResult) {
            saveNewAlbum(album).throwIfFailure()
        }

        collectionsLocalDataSource.addAlbumToAlbumCollectionInAlbumCollectionCrossRef(
            AlbumCollectionCrossRefDTO(
                collectionId = albumCollection.collectionId!!,
                albumId = album.albumId
            )
        ).throwIfFailure()

        collectionsLocalDataSource.updateLastUpdated(
            albumCollection.collectionId,
            System.currentTimeMillis()
        ).throwIfFailure().let { _ ->
            refreshListOfCollections()
        }
    }

    override suspend fun setSortingType(sortingType: CollectionsSortingType, screenKey: ScreenKey) {
        sharedPreferences
            .edit()
            .putString("$screenKey-sortingType", sortingType.name)
            .apply()

        updateSortedCollectionsList(screenKey, sortingType)
    }

    override fun getStoredSortingType(screenKey: ScreenKey): CollectionsSortingType {
        val sortingTypeName = sharedPreferences.getString(
            "$screenKey-sortingType",
            CollectionsSortingType.ALPHABETICAL_A_Z.name
        )
        return CollectionsSortingType.valueOf(sortingTypeName!!)
    }

    override suspend fun deleteCollection(albumCollection: AlbumCollection) {
        collectionsLocalDataSource
            .deleteCollection(albumCollection.asDatabaseModel())
            .throwIfFailure()

        refreshListOfCollections(forceUpdate = true)
    }

    override fun getCollectionsWithAlbumsList(screenKey: ScreenKey): StateFlow<List<AlbumCollectionWithAlbums>> {
        val storedSortingType = getStoredSortingType(screenKey)
        updateSortedCollectionsList(screenKey, storedSortingType)

        return _sortedCollectionsWithAlbumsListsByScreen[screenKey]!!
    }

    private suspend fun saveNewAlbum(album: Album): Result<Unit> = withContext(ioDispatcher) {
        return@withContext runCatching {
            if (album.imageFilePath.contains("http")) {
                imageService.downloadImage(album.imageFilePath)
                    .throwIfFailure()
                    .let { downloadResult ->
                        if (downloadResult.isSuccess) {
                            val bitmap = downloadResult.getOrNull()!!
                            album.imageFilePath =
                                imageService
                                    .saveImageToFile(bitmap, UUID.randomUUID().toString())
                                    .throwIfFailure()
                                    .getOrNull()!!
                        } else {
                            Timber.e("There was an issue downloading image for album: $album " + downloadResult.exceptionOrNull())
                        }
                    }
            }

            collectionsLocalDataSource.insertAlbum(album.asDatabaseModel()).throwIfFailure()
                .getOrNull()!!
        }
    }

    private fun sortCollectionsWithAlbumsList(
        sortingType: CollectionsSortingType,
        list: List<AlbumCollectionWithAlbums>
    ): List<AlbumCollectionWithAlbums> {
        return when (sortingType) {
            CollectionsSortingType.ALPHABETICAL_A_Z -> list.sortedBy { it.collection.name }
            CollectionsSortingType.ALPHABETICAL_Z_A -> list.sortedByDescending { it.collection.name }
            CollectionsSortingType.RECENTLY_UPDATED -> list.sortedBy { it.collection.lastUpdated }
            CollectionsSortingType.LEAST_RECENTLY_UPDATED -> list.sortedByDescending { it.collection.lastUpdated }
            CollectionsSortingType.SMALLEST_TO_LARGEST -> list.sortedBy { it.albums.size }
            CollectionsSortingType.LARGEST_TO_SMALLEST -> list.sortedByDescending { it.albums.size }
        }
    }

    private fun updateSortedCollectionsList(
        screenKey: ScreenKey,
        sortingType: CollectionsSortingType
    ) {
        val sortedListStateFlow = _sortedCollectionsWithAlbumsListsByScreen[screenKey]
        val sortedList =
            sortCollectionsWithAlbumsList(sortingType, _collectionsWithAlbumsList.value)
        if (sortedListStateFlow != null) {
            sortedListStateFlow.value = sortedList
        } else {
            val newSortedListStateFlow = MutableStateFlow(sortedList)
            _sortedCollectionsWithAlbumsListsByScreen[screenKey] = newSortedListStateFlow
        }
    }

}
