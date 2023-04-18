package gg.jrg.audiminder.collections.domain.model

import gg.jrg.audiminder.collections.data.dto.AlbumCollectionDTO
import gg.jrg.audiminder.core.domain.DTOMappable
import java.util.*

data class AlbumCollection(
    val collectionId: Int,
    val name: String,
    val lastOpened: Date
) : DTOMappable<AlbumCollectionDTO> {
    override fun asDatabaseModel(): AlbumCollectionDTO = AlbumCollectionDTO(
        collectionId = this.collectionId,
        name = this.name,
        lastOpened = this.lastOpened
    )
}
