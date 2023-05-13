package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase

class RefreshListOfCollectionsSuspendUseCase(private val collectionsRepository: CollectionsRepository) :
    SuspendUseCase<NoInput, Unit> {

    override suspend fun invoke(input: NoInput) {
        return collectionsRepository.refreshListOfCollections()
    }

}