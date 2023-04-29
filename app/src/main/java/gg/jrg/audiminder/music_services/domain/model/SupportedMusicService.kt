package gg.jrg.audiminder.music_services.domain.model

import gg.jrg.audiminder.core.domain.DTOMappable
import gg.jrg.audiminder.music_services.data.dto.SupportedMusicServiceDTO

data class SupportedMusicService(
    val serviceId: Int,
    val serviceName: String
) : DTOMappable<SupportedMusicServiceDTO> {
    override fun asDatabaseModel(): SupportedMusicServiceDTO =
        SupportedMusicServiceDTO(
            serviceId = this.serviceId,
            serviceName = this.serviceName
        )
}
