package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.util.CollectionsSortingType
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase
import gg.jrg.audiminder.core.util.ScreenKey

class SetStoredSortingTypeSuspendUseCase(
    private val collectionsRepository: CollectionsRepository
) : SuspendUseCase<SetStoredSortingTypeInputParameters, Unit> {
    override suspend fun invoke(input: SetStoredSortingTypeInputParameters) =
        collectionsRepository.setSortingType(input.sortingType, input.screenKey)
}

data class SetStoredSortingTypeInputParameters(
    val screenKey: ScreenKey,
    val sortingType: CollectionsSortingType
)