package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase

class AddAlbumToAlbumCollectionSuspendUseCase(
    private val collectionsRepository: CollectionsRepository
) : SuspendUseCase<AddAlbumToAlbumCollectionInputParameters, Unit> {
    override suspend fun invoke(input: AddAlbumToAlbumCollectionInputParameters) {
        collectionsRepository.addAlbumToCollection(
            album = input.album,
            albumCollection = input.collection
        )
    }
}

data class AddAlbumToAlbumCollectionInputParameters(
    val album: Album,
    val collection: AlbumCollection
)