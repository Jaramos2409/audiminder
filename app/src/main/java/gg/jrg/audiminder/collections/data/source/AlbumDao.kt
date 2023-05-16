package gg.jrg.audiminder.collections.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gg.jrg.audiminder.collections.data.dto.AlbumDTO

@Dao
interface AlbumDao {

    @Query("SELECT EXISTS (SELECT 1 FROM albums WHERE albumId = :id)")
    suspend fun doesAlbumExistInDatabase(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumDTO: AlbumDTO)

}