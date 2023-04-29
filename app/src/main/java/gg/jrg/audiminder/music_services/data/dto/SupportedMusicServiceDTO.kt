package gg.jrg.audiminder.music_services.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import gg.jrg.audiminder.core.data.DomainMappable
import gg.jrg.audiminder.music_services.domain.model.SupportedMusicService

@Entity(tableName = "supported_auth_services")
data class SupportedMusicServiceDTO(
    @PrimaryKey(autoGenerate = true) val serviceId: Int = 0,
    val serviceName: String
) : DomainMappable<SupportedMusicService> {
    override fun asDomainModel(): SupportedMusicService = SupportedMusicService(
        serviceId = this.serviceId,
        serviceName = this.serviceName
    )
}
