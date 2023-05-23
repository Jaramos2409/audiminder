package gg.jrg.audiminder.collections.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionDTO
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionWithAlbumsDTO

@Dao
interface AlbumCollectionDao {

    @Transaction
    @Query("SELECT * FROM collections")
    fun getCollectionsWithAlbums(): List<AlbumCollectionWithAlbumsDTO>

    @Query("SELECT MAX(lastUpdated) FROM collections")
    suspend fun getLatestUpdate(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumCollection(albumCollectionDTO: AlbumCollectionDTO): Long

    @Query("UPDATE collections SET imageFilePath = :newImageFilePath WHERE collectionId = :id")
    suspend fun updateImageFilePath(id: Int, newImageFilePath: String?)

    @Query("SELECT imageFilePath FROM collections WHERE collectionId = :id")
    suspend fun getImageFilePath(id: Int): String?

    @Query("UPDATE collections SET lastUpdated = :lastUpdated WHERE collectionId = :id")
    suspend fun updateLastUpdated(id: Int, lastUpdated: Long)

    @Transaction
    @Query("SELECT * FROM collections WHERE collectionId = :collectionId")
    fun getCollectionWithAlbums(collectionId: Int): AlbumCollectionWithAlbumsDTO

    @Delete
    fun deleteCollection(albumCollectionDTO: AlbumCollectionDTO): Int

}