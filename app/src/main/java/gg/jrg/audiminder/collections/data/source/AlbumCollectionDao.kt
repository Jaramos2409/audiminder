package gg.jrg.audiminder.collections.data.source

import androidx.room.Dao
import androidx.room.Query
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionDTO

@Dao
interface AlbumCollectionDao {

    @Query("SELECT * FROM collections")
    suspend fun getAlbumCollections(): List<AlbumCollectionDTO>

    @Query("SELECT MAX(lastUpdated) FROM collections")
    suspend fun getLatestUpdate(): Long?

}