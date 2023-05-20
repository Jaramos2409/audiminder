package gg.jrg.audiminder.collections.domain.model

import gg.jrg.audiminder.collections.data.dto.AlbumCollectionWithAlbumsDTO
import gg.jrg.audiminder.core.domain.DTOMappable
import gg.jrg.audiminder.core.domain.asDatabaseModelList

data class AlbumCollectionWithAlbums(
    val collection: AlbumCollection,
    val albums: List<Album>
) : DTOMappable<AlbumCollectionWithAlbumsDTO> {
    override fun asDatabaseModel(): AlbumCollectionWithAlbumsDTO = AlbumCollectionWithAlbumsDTO(
        collection = this.collection.asDatabaseModel(),
        albums = this.albums.asDatabaseModelList()
    )
}
