package gg.jrg.audiminder.collections.presentation.add_to_existing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.collections.domain.model.CollectionsManager
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.core.util.ScreenKey
import gg.jrg.audiminder.core.util.logChanges
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToExistingCollectionViewModel @Inject constructor(
    collectionsUseCases: CollectionsUseCases
) : ViewModel() {

    val collectionsList = CollectionsManager(
        collectionsUseCases,
        ScreenKey.ADD_TO_EXISTING_SCREEN
    ).apply { logChanges("AddToExistingCollectionViewModel _collectionsList") }

    init {
        viewModelScope.launch {
            collectionsList.refreshListOfCollections()
        }
    }

    fun saveAlbumToAlbumCollection(album: Album, albumCollection: AlbumCollection): Job =
        viewModelScope.launch {
            collectionsList.addAlbumToCollection(
                album = album,
                collection = albumCollection
            )
        }

}