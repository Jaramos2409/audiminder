package gg.jrg.audiminder.collections.domain.usecase

import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.core.util.ScreenKey
import kotlinx.coroutines.flow.StateFlow

class GetCollectionsStateFlowUseCase(
    private val collectionsRepository: CollectionsRepository
) : UseCase<ScreenKey, StateFlow<List<AlbumCollectionWithAlbums>>> {
    override fun invoke(input: ScreenKey): StateFlow<List<AlbumCollectionWithAlbums>> =
        collectionsRepository.getCollectionsWithAlbumsList(input)
}