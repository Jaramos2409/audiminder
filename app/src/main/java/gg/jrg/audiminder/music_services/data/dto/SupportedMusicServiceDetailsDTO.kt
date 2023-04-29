package gg.jrg.audiminder.music_services.data.dto

import androidx.room.*
import gg.jrg.audiminder.core.data.DomainMappable
import gg.jrg.audiminder.music_services.domain.model.SupportedMusicServiceDetails

@Entity(
    tableName = "supported_music_service_details",
    foreignKeys = [
        ForeignKey(
            entity = SupportedMusicServiceDTO::class,
            parentColumns = ["serviceId"],
            childColumns = ["serviceId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["serviceId"])]
)
data class SupportedMusicServiceDetailsDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "detailsId")
    val detailsId: Int = 0,
    @ColumnInfo(name = "serviceId")
    val serviceId: Int,
    @ColumnInfo(name = "detailKey")
    val detailKey: String,
    @ColumnInfo(name = "detailValue")
    val detailValue: String
) : DomainMappable<SupportedMusicServiceDetails> {
    override fun asDomainModel(): SupportedMusicServiceDetails = SupportedMusicServiceDetails(
        detailsId = this.detailsId,
        serviceId = this.serviceId,
        detailKey = this.detailKey,
        detailValue = this.detailValue
    )
}