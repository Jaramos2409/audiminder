package gg.jrg.audiminder.collections.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.core.data.DomainMappable

@Entity(tableName = "albums")
data class AlbumDTO(
    @PrimaryKey(autoGenerate = true) val albumId: Int,
    val name: String,
    val artist: String,
    val imageFilePath: String,
    val serviceId: Int,
    val serviceSpecificURI: String
) : DomainMappable<Album> {
    override fun asDomainModel(): Album = Album(
        albumId = this.albumId,
        name = this.name,
        artist = this.artist,
        imageFilePath = this.imageFilePath,
        serviceId = this.serviceId,
        serviceSpecificURI = this.serviceSpecificURI
    )
}
