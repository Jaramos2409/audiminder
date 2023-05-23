package gg.jrg.audiminder.collections.domain.model

import android.os.Parcelable
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionDTO
import gg.jrg.audiminder.core.domain.DTOMappable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumCollection(
    val collectionId: Int? = null,
    val name: String,
    val lastOpened: Long = System.currentTimeMillis(),
    val lastUpdated: Long = System.currentTimeMillis(),
    val imageFilePath: String? = null
) : DTOMappable<AlbumCollectionDTO>, Parcelable {
    override fun asDatabaseModel(): AlbumCollectionDTO = AlbumCollectionDTO(
        collectionId = this.collectionId,
        name = this.name,
        lastOpened = this.lastOpened,
        lastUpdated = this.lastUpdated,
        imageFilePath = this.imageFilePath
    )
}
