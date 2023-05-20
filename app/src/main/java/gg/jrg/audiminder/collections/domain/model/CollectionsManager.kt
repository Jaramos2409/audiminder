package gg.jrg.audiminder.collections.domain.model

import gg.jrg.audiminder.collections.domain.usecase.AddAlbumToAlbumCollectionInputParameters
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.collections.domain.usecase.SetStoredSortingTypeInputParameters
import gg.jrg.audiminder.collections.util.CollectionsSortingType
import gg.jrg.audiminder.core.util.ScreenKey
import kotlinx.coroutines.flow.StateFlow

class CollectionsManager(
    private val collectionsUseCases: CollectionsUseCases,
    private val screenKey: ScreenKey
) : StateFlow<List<AlbumCollectionWithAlbums>> by collectionsUseCases.getCollectionsStateFlowUseCase(
    screenKey
) {

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

    fun getStoredSortingType(): CollectionsSortingType =
        collectionsUseCases.getStoredSortingTypeUseCase(screenKey)

    suspend fun setStoredSortingType(sortingType: CollectionsSortingType) {
        collectionsUseCases.setStoredSortingTypeSuspendUseCase(
            SetStoredSortingTypeInputParameters(
                screenKey = screenKey,
                sortingType = sortingType
            )
        )
    }
}