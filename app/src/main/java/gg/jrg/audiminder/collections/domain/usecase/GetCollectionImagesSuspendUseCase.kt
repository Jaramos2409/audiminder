package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase

class GetCollectionImagesSuspendUseCase(
    private val collectionsRepository: CollectionsRepository
) : SuspendUseCase<AlbumCollection, List<Album>> {
    override suspend fun invoke(input: AlbumCollection): List<Album> =
        collectionsRepository.getCollectionImages(input)
}