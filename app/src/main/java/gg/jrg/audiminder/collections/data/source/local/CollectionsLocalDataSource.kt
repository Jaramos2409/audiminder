package gg.jrg.audiminder.collections.data.source.local

import gg.jrg.audiminder.collections.data.dto.AlbumCollectionCrossRefDTO
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionDTO
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionWithAlbumsDTO
import gg.jrg.audiminder.collections.data.dto.AlbumDTO
import gg.jrg.audiminder.collections.data.source.AlbumCollectionCrossRefDao
import gg.jrg.audiminder.collections.data.source.AlbumCollectionDao
import gg.jrg.audiminder.collections.data.source.AlbumDao
import gg.jrg.audiminder.collections.data.source.TrackDao
import gg.jrg.audiminder.core.data.source.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CollectionsLocalDataSource {
    suspend fun getCollectionsWithAlbums(): Result<List<AlbumCollectionWithAlbumsDTO>>
    suspend fun getLatestUpdate(): Result<Long?>
    suspend fun insertAlbumCollection(albumCollectionDTO: AlbumCollectionDTO): Result<AlbumCollectionDTO>
    suspend fun doesAlbumExistInDatabase(id: String): Result<Boolean>
    suspend fun insertAlbum(albumDTO: AlbumDTO): Result<Unit>
    suspend fun addAlbumToAlbumCollectionInAlbumCollectionCrossRef(
        albumCollectionCrossRefDTO: AlbumCollectionCrossRefDTO
    ): Result<Unit>
    suspend fun hasFourOrMoreAlbums(collectionId: Int): Result<Boolean>
    suspend fun getRandomFourAlbums(collectionId: Int): Result<List<AlbumDTO>>
    suspend fun updateCollectionCollage(collectionId: Int, imageFilePath: String?): Result<Unit>
    suspend fun getCollectionCollage(collectionId: Int): Result<String?>
    suspend fun updateLastUpdated(collectionId: Int, lastUpdated: Long): Result<Unit>
    suspend fun deleteCollection(albumCollectionDTO: AlbumCollectionDTO): Result<Unit>
}

class CollectionsLocalDataSourceImpl @Inject constructor(
    private val albumCollectionDao: AlbumCollectionDao,
    private val albumCollectionCrossRefDao: AlbumCollectionCrossRefDao,
    private val albumDao: AlbumDao,
    private val trackDao: TrackDao,
    private val appDatabase: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CollectionsLocalDataSource {

    override suspend fun getCollectionsWithAlbums(): Result<List<AlbumCollectionWithAlbumsDTO>> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                return@runCatching albumCollectionDao.getCollectionsWithAlbums()
            }
        }

    override suspend fun getLatestUpdate(): Result<Long?> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                albumCollectionDao.getLatestUpdate() ?: throw Exception("No latest update found")
            }
        }

    override suspend fun insertAlbumCollection(albumCollectionDTO: AlbumCollectionDTO): Result<AlbumCollectionDTO> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                val id = albumCollectionDao.insertAlbumCollection(albumCollectionDTO).toInt()
                return@runCatching albumCollectionDTO.apply {
                    this.collectionId = id
                }
            }
        }

    override suspend fun doesAlbumExistInDatabase(id: String): Result<Boolean> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                return@runCatching albumDao.doesAlbumExistInDatabase(id)
            }
        }

    override suspend fun insertAlbum(albumDTO: AlbumDTO): Result<Unit> = withContext(ioDispatcher) {
        return@withContext runCatching {
            albumDao.insertAlbum(albumDTO)
        }
    }

    override suspend fun addAlbumToAlbumCollectionInAlbumCollectionCrossRef(
        albumCollectionCrossRefDTO: AlbumCollectionCrossRefDTO
    ): Result<Unit> = withContext(ioDispatcher) {
        return@withContext runCatching {
            albumCollectionCrossRefDao.addAlbumToAlbumCollectionInAlbumCollectionCrossRef(
                albumCollectionCrossRefDTO
            )
        }
    }

    override suspend fun hasFourOrMoreAlbums(collectionId: Int): Result<Boolean> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                albumCollectionCrossRefDao.hasFourOrMoreAlbums(collectionId)
            }
        }

    override suspend fun getRandomFourAlbums(collectionId: Int): Result<List<AlbumDTO>> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                val albumIds = albumCollectionCrossRefDao.getRandomFourAlbumIds(collectionId)
                val albums = mutableListOf<AlbumDTO>()
                albumIds.forEach { albumId ->
                    albums.add(albumDao.getAlbumById(albumId)!!)
                }
                return@runCatching albums
            }
        }

    override suspend fun updateCollectionCollage(
        collectionId: Int,
        imageFilePath: String?
    ): Result<Unit> = withContext(ioDispatcher) {
        return@withContext runCatching {
            albumCollectionDao.updateImageFilePath(collectionId, imageFilePath)
        }
    }

    override suspend fun getCollectionCollage(collectionId: Int): Result<String?> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                albumCollectionDao.getImageFilePath(collectionId)
            }
        }

    override suspend fun updateLastUpdated(collectionId: Int, lastUpdated: Long): Result<Unit> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                albumCollectionDao.updateLastUpdated(collectionId, lastUpdated)
            }
        }

    override suspend fun deleteCollection(albumCollectionDTO: AlbumCollectionDTO): Result<Unit> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                appDatabase.runInTransaction {
                    albumCollectionCrossRefDao.deleteAlbumCollectionCrossRefsByCollectionId(
                        albumCollectionDTO.collectionId!!
                    )
                    albumCollectionDao.deleteCollection(albumCollectionDTO)
                }
            }
        }

}
