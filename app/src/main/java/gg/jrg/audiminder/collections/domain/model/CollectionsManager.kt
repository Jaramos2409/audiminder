package gg.jrg.audiminder.collections.domain.model

import gg.jrg.audiminder.collections.domain.usecase.AddAlbumToAlbumCollectionInputParameters
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import kotlinx.coroutines.flow.StateFlow

class CollectionsManager(
    private val collectionsUseCases: CollectionsUseCases
) : StateFlow<List<AlbumCollection>> by collectionsUseCases.getCollectionsStateFlowUseCase() {

    suspend fun refreshListOfCollections() {
        collectionsUseCases.refreshListOfCollectionsSuspendUseCase()
    }

    suspend fun addAlbumToCollection(
        album: Album,
        collection: AlbumCollection
    ) {
        collectionsUseCases.addAlbumToAlbumCollectionSuspendUseCase(
            AddAlbumToAlbumCollectionInputParameters(
                album = album,
                collection = collection
            )
        )
    }

}