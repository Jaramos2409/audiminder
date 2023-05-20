package gg.jrg.audiminder.collections.data.repositories

import android.content.SharedPreferences
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionCrossRefDTO
import gg.jrg.audiminder.collections.data.source.local.CollectionsLocalDataSource
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.core.data.asDomainModelList
import gg.jrg.audiminder.core.util.ImageService
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
    val collectionsWithAlbumsList: StateFlow<List<AlbumCollectionWithAlbums>>
    suspend fun refreshListOfCollections()
    suspend fun insertCollection(albumCollection: AlbumCollection): AlbumCollection
    suspend fun addAlbumToCollection(album: Album, albumCollection: AlbumCollection)
    suspend fun getCollectionImages(collection: AlbumCollection): List<Album>
}

class CollectionsRepositoryImpl @Inject constructor(
    private val collectionsLocalDataSource: CollectionsLocalDataSource,
    private val sharedPreferences: SharedPreferences,
    private val imageService: ImageService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CollectionsRepository {

    private val _collectionsWithAlbumsList =
        MutableStateFlow(emptyList<AlbumCollectionWithAlbums>())
    override val collectionsWithAlbumsList: StateFlow<List<AlbumCollectionWithAlbums>>
        get() = _collectionsWithAlbumsList

    override suspend fun refreshListOfCollections() {
        val latestUpdate = collectionsLocalDataSource.getLatestUpdate()
        val lastFetchTime = sharedPreferences.getLong("lastFetchTime", 0)

        if (latestUpdate.isSuccess && latestUpdate.getOrNull()!! > lastFetchTime) {
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

    override suspend fun getCollectionImages(collection: AlbumCollection): List<Album> =
        collectionsLocalDataSource
            .getCollectionImagePaths(collection.collectionId!!)
            .throwIfFailure()
            .getOrNull()!!
            .asDomainModelList()

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

}