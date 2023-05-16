package gg.jrg.audiminder.collections.data.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.data.DomainMappable

@Entity(
    tableName = "collections",
    indices = [Index(value = ["lastUpdated"])]
)
data class AlbumCollectionDTO(
    @PrimaryKey(autoGenerate = true) var collectionId: Int? = null,
    val name: String,
    val lastOpened: Long = System.currentTimeMillis(),
    val lastUpdated: Long = System.currentTimeMillis()
) : DomainMappable<AlbumCollection> {
    override fun asDomainModel(): AlbumCollection = AlbumCollection(
        collectionId = this.collectionId,
        name = this.name,
        lastOpened = this.lastOpened
    )
}

