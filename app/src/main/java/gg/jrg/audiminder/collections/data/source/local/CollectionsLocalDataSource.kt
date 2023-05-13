package gg.jrg.audiminder.collections.data.source.local

import gg.jrg.audiminder.collections.data.dto.AlbumCollectionDTO
import gg.jrg.audiminder.collections.data.source.AlbumCollectionCrossRefDao
import gg.jrg.audiminder.collections.data.source.AlbumCollectionDao
import gg.jrg.audiminder.collections.data.source.AlbumDao
import gg.jrg.audiminder.collections.data.source.TrackDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CollectionsLocalDataSource {
    suspend fun getAlbumCollections(): Result<List<AlbumCollectionDTO>?>
    suspend fun getLatestUpdate(): Result<Long?>
}

class CollectionsLocalDataSourceImpl @Inject constructor(
    private val albumCollectionDao: AlbumCollectionDao,
    private val albumCollectionCrossRefDao: AlbumCollectionCrossRefDao,
    private val albumDao: AlbumDao,
    private val trackDao: TrackDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CollectionsLocalDataSource {

    override suspend fun getAlbumCollections(): Result<List<AlbumCollectionDTO>?> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                albumCollectionDao.getAlbumCollections()
            }
        }

    override suspend fun getLatestUpdate(): Result<Long?> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                albumCollectionDao.getLatestUpdate() ?: throw Exception("No latest update found")
            }
        }


}
