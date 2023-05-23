package gg.jrg.audiminder.collections.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionCrossRefDTO

@Dao
interface AlbumCollectionCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbumToAlbumCollectionInAlbumCollectionCrossRef(albumCollectionCrossRefDTO: AlbumCollectionCrossRefDTO)

    @Query("SELECT COUNT(albumId) >= 4 FROM collection_album_join WHERE collectionId = :collectionId")
    suspend fun hasFourOrMoreAlbums(collectionId: Int): Boolean

    @Query("SELECT albumId FROM collection_album_join WHERE collectionId = :collectionId ORDER BY RANDOM() LIMIT 4")
    suspend fun getRandomFourAlbumIds(collectionId: Int): List<String>

    @Query("DELETE FROM collection_album_join WHERE collectionId = :collectionId")
    fun deleteAlbumCollectionCrossRefsByCollectionId(collectionId: Int): Int

}
