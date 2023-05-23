package gg.jrg.audiminder.collections.domain.model

import android.os.Parcelable
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionWithAlbumsDTO
import gg.jrg.audiminder.core.domain.DTOMappable
import gg.jrg.audiminder.core.domain.asDatabaseModelList
import gg.jrg.audiminder.core.presentation.BindableView
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumCollectionWithAlbums(
    val collection: AlbumCollection,
    val albums: List<Album>
) : DTOMappable<AlbumCollectionWithAlbumsDTO>, BindableView, Parcelable {

    override val title: String
        get() = collection.name

    override val subtitle: String
        get() = getArtists()

    override fun getImageFilePaths(): List<String> =
        albums.map { it.imageFilePath }.shuffled().distinct().take(4)

    override fun asDatabaseModel(): AlbumCollectionWithAlbumsDTO = AlbumCollectionWithAlbumsDTO(
        collection = this.collection.asDatabaseModel(),
        albums = this.albums.asDatabaseModelList()
    )

    private fun getArtists(): String {
        return albums
            .asSequence()
            .shuffled()
            .map { it.artist }
            .distinct()
            .take(5)
            .joinToString(", ")
    }

}
