package gg.jrg.audiminder.collections.domain.model

import gg.jrg.audiminder.collections.data.dto.TrackDTO
import gg.jrg.audiminder.core.domain.DTOMappable

data class Track(
    val trackId: Int,
    val name: String,
    val albumId: Int,
    val serviceId: Int,
    val serviceSpecificURI: String
) : DTOMappable<TrackDTO> {
    override fun asDatabaseModel(): TrackDTO = TrackDTO(
        trackId = this.trackId,
        name = this.name,
        albumId = this.albumId,
        serviceId = this.serviceId,
        serviceSpecificURI = this.serviceSpecificURI
    )
}
