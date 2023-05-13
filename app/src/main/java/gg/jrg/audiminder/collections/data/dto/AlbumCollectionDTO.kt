package gg.jrg.audiminder.collections.data.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.data.DomainMappable
import java.util.Date

@Entity(
    tableName = "collections",
    indices = [Index(value = ["lastUpdated"])]

)
data class AlbumCollectionDTO(
    @PrimaryKey(autoGenerate = true) val collectionId: Int,
    val name: String,
    val lastOpened: Date,
    val lastUpdated: Long = System.currentTimeMillis()
) : DomainMappable<AlbumCollection> {
    override fun asDomainModel(): AlbumCollection = AlbumCollection(
        collectionId = this.collectionId,
        name = this.name,
        lastOpened = this.lastOpened
    )
}

