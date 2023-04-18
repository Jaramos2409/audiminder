package gg.jrg.audiminder.collections.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import gg.jrg.audiminder.collections.domain.model.Track
import gg.jrg.audiminder.core.data.DomainMappable

@Entity(tableName = "tracks")
data class TrackDTO(
    @PrimaryKey(autoGenerate = true) val trackId: Int,
    val name: String,
    val albumId: Int,
    val serviceId: Int,
    val serviceSpecificURI: String
) : DomainMappable<Track> {
    override fun asDomainModel(): Track = Track(
        trackId = this.trackId,
        name = this.name,
        albumId = this.albumId,
        serviceId = this.serviceId,
        serviceSpecificURI = this.serviceSpecificURI
    )
}