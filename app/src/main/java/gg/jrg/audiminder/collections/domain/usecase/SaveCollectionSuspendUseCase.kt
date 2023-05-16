package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase

class SaveCollectionSuspendUseCase(
    private val collectionsRepository: CollectionsRepository
) : SuspendUseCase<AlbumCollection, AlbumCollection> {
    override suspend fun invoke(input: AlbumCollection): AlbumCollection =
        collectionsRepository.insertCollection(input)

}