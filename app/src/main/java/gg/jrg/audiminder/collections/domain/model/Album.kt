package gg.jrg.audiminder.collections.domain.model

import android.os.Parcelable
import gg.jrg.audiminder.collections.data.dto.AlbumDTO
import gg.jrg.audiminder.core.domain.DTOMappable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val albumId: String = "",
    val name: String = "",
    val artist: String = "",
    var imageFilePath: String = "",
    val serviceId: Int = 0,
    val serviceSpecificURI: String = ""
) : Parcelable, DTOMappable<AlbumDTO> {
    override fun asDatabaseModel(): AlbumDTO = AlbumDTO(
        albumId = this.albumId,
        name = this.name,
        artist = this.artist,
        imageFilePath = this.imageFilePath,
        serviceId = this.serviceId,
        serviceSpecificURI = this.serviceSpecificURI
    )
}
