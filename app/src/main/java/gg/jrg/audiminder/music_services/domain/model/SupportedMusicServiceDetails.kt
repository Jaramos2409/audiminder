package gg.jrg.audiminder.music_services.domain.model

import gg.jrg.audiminder.core.domain.DTOMappable
import gg.jrg.audiminder.music_services.data.dto.SupportedMusicServiceDetailsDTO

data class SupportedMusicServiceDetails(
    val detailsId: Int = 0,
    val serviceId: Int,
    val detailKey: String,
    val detailValue: String
) : DTOMappable<SupportedMusicServiceDetailsDTO> {
    override fun asDatabaseModel(): SupportedMusicServiceDetailsDTO =
        SupportedMusicServiceDetailsDTO(
            detailsId = this.detailsId,
            serviceId = this.serviceId,
            detailKey = this.detailKey,
            detailValue = this.detailValue
        )
}
