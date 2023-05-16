package gg.jrg.audiminder.collections.domain.model

import gg.jrg.audiminder.collections.data.dto.AlbumCollectionCrossRefDTO
import gg.jrg.audiminder.core.domain.DTOMappable

data class AlbumCollectionCrossRef(
    val collectionId: Int,
    val albumId: String
) : DTOMappable<AlbumCollectionCrossRefDTO> {
    override fun asDatabaseModel(): AlbumCollectionCrossRefDTO = AlbumCollectionCrossRefDTO(
        collectionId = this.collectionId,
        albumId = this.albumId
    )
}