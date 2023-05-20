package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import kotlinx.coroutines.flow.StateFlow

class GetCollectionsStateFlowUseCase(
    private val collectionsRepository: CollectionsRepository
) : UseCase<NoInput, StateFlow<List<AlbumCollectionWithAlbums>>> {
    override fun invoke(input: NoInput): StateFlow<List<AlbumCollectionWithAlbums>> =
        collectionsRepository.collectionsWithAlbumsList
}