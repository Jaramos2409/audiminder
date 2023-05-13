package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import javax.inject.Inject

class CollectionsUseCases @Inject constructor(
    collectionsRepository: CollectionsRepository
) {
    val refreshListOfCollectionsSuspendUseCase =
        RefreshListOfCollectionsSuspendUseCase(collectionsRepository)
    val getCollectionsStateFlowUseCase =
        GetCollectionsStateFlowUseCase(collectionsRepository)
}