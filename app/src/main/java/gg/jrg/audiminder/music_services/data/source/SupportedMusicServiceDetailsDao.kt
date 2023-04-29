package gg.jrg.audiminder.music_services.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import gg.jrg.audiminder.music_services.data.dto.SupportedMusicServiceDetailsDTO

@Dao
interface SupportedMusicServiceDetailsDao {
    @Insert
    suspend fun insert(serviceDetails: SupportedMusicServiceDetailsDTO): Long

    @Query("SELECT * FROM supported_music_service_details WHERE serviceId = :serviceId")
    suspend fun getSupportedMusicServiceDetailsByServiceId(serviceId: Int): List<SupportedMusicServiceDetailsDTO>
}