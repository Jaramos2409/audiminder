package gg.jrg.audiminder.collections.data.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.core.data.DomainMappable
import gg.jrg.audiminder.core.data.asDomainModelList

data class AlbumCollectionWithAlbumsDTO(
    @Embedded val collection: AlbumCollectionDTO,
    @Relation(
        parentColumn = "collectionId",
        entityColumn = "albumId",
        associateBy = Junction(AlbumCollectionCrossRefDTO::class)
    )
    val albums: List<AlbumDTO>
) : DomainMappable<AlbumCollectionWithAlbums> {
    override fun asDomainModel(): AlbumCollectionWithAlbums = AlbumCollectionWithAlbums(
        collection = this.collection.asDomainModel(),
        albums = this.albums.asDomainModelList()
    )
}
