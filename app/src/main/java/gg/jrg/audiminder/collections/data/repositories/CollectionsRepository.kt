package gg.jrg.audiminder.collections.data.repositories

import android.content.SharedPreferences
import gg.jrg.audiminder.collections.data.source.local.CollectionsLocalDataSource
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.data.asDomainModelList
import gg.jrg.audiminder.core.util.throwIfFailure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface CollectionsRepository {
    val collectionsList: StateFlow<List<AlbumCollection>>
    suspend fun refreshListOfCollections()
    suspend fun insertCollection(albumCollection: AlbumCollection)
}

class CollectionsRepositoryImpl @Inject constructor(
    private val collectionsLocalDataSource: CollectionsLocalDataSource,
    private val sharedPreferences: SharedPreferences
) : CollectionsRepository {

    private val _collectionsList = MutableStateFlow(emptyList<AlbumCollection>())
    override val collectionsList: StateFlow<List<AlbumCollection>>
        get() = _collectionsList

    override suspend fun refreshListOfCollections() {
        val latestUpdate = collectionsLocalDataSource.getLatestUpdate()
        val lastFetchTime = sharedPreferences.getLong("lastFetchTime", 0)

        if (latestUpdate.isSuccess && latestUpdate.getOrNull()!! > lastFetchTime) {
            val listOfAllCollectionsResult = collectionsLocalDataSource.getAlbumCollections()

            if (listOfAllCollectionsResult.isSuccess) {
                _collectionsList.value =
                    listOfAllCollectionsResult.getOrNull()!!.asDomainModelList()
                sharedPreferences.edit().putLong("lastFetchTime", System.currentTimeMillis())
                    .apply()
            } else {
                throw Exception(listOfAllCollectionsResult.exceptionOrNull())
            }
        }
    }

    override suspend fun insertCollection(albumCollection: AlbumCollection) {
        collectionsLocalDataSource.insertAlbumCollection(albumCollection.asDatabaseModel())
            .throwIfFailure()
    }

}