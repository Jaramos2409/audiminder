package gg.jrg.audiminder.collections.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.data.DomainMappable
import java.util.*

@Entity(tableName = "collections")
data class AlbumCollectionDTO(
    @PrimaryKey(autoGenerate = true) val collectionId: Int,
    val name: String,
    val lastOpened: Date
) : DomainMappable<AlbumCollection> {
    override fun asDomainModel(): AlbumCollection = AlbumCollection(
        collectionId = this.collectionId,
        name = this.name,
        lastOpened = this.lastOpened
    )
}

