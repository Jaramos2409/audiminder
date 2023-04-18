package gg.jrg.audiminder.collections.domain.model

import gg.jrg.audiminder.collections.data.dto.AlbumDTO
import gg.jrg.audiminder.core.domain.DTOMappable

data class Album(
    val albumId: Int,
    val name: String,
    val artist: String,
    val imageFilePath: String,
    val serviceId: Int,
    val serviceSpecificURI: String
) : DTOMappable<AlbumDTO> {
    override fun asDatabaseModel(): AlbumDTO = AlbumDTO(
        albumId = this.albumId,
        name = this.name,
        artist = this.artist,
        imageFilePath = this.imageFilePath,
        serviceId = this.serviceId,
        serviceSpecificURI = this.serviceSpecificURI
    )
}
