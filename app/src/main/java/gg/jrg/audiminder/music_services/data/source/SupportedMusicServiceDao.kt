package gg.jrg.audiminder.music_services.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gg.jrg.audiminder.music_services.data.dto.SupportedMusicServiceDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface SupportedMusicServiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg supportedMusicServiceDTO: SupportedMusicServiceDTO)

    @Query("SELECT * FROM supported_auth_services")
    fun getAllSupportedMusicServices(): Flow<List<SupportedMusicServiceDTO>>

}