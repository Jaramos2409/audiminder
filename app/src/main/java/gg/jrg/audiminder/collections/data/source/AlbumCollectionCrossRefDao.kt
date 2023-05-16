package gg.jrg.audiminder.collections.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionCrossRefDTO

@Dao
interface AlbumCollectionCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbumToAlbumCollectionInAlbumCollectionCrossRef(albumCollectionCrossRefDTO: AlbumCollectionCrossRefDTO)


}