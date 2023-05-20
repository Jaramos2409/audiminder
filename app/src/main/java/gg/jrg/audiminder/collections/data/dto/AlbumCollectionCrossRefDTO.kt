package gg.jrg.audiminder.collections.data.dto

import androidx.room.Entity
import androidx.room.Index
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionCrossRef
import gg.jrg.audiminder.core.data.DomainMappable

@Entity(
    tableName = "collection_album_join",
    primaryKeys = ["collectionId", "albumId"],
    indices = [Index(value = ["albumId"])]
)
data class AlbumCollectionCrossRefDTO(
    val collectionId: Int,
    val albumId: String
) : DomainMappable<AlbumCollectionCrossRef> {
    override fun asDomainModel(): AlbumCollectionCrossRef = AlbumCollectionCrossRef(
        collectionId = this.collectionId,
        albumId = this.albumId
    )
}