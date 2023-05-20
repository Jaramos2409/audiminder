package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.util.CollectionsSortingType
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.core.util.ScreenKey

class GetStoredSortingTypeUseCase(
    private val collectionsRepository: CollectionsRepository
) : UseCase<ScreenKey, CollectionsSortingType> {
    override fun invoke(input: ScreenKey): CollectionsSortingType =
        collectionsRepository.getStoredSortingType(input)
}