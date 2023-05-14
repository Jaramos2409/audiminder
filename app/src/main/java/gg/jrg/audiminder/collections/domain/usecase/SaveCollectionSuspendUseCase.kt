package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase

class SaveCollectionSuspendUseCase(
    private val collectionsRepository: CollectionsRepository
) : SuspendUseCase<AlbumCollection, Unit> {
    override suspend fun invoke(input: AlbumCollection) {
        collectionsRepository.insertCollection(input)
    }
}