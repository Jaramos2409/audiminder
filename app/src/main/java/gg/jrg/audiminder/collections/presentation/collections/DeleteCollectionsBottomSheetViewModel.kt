package gg.jrg.audiminder.collections.presentation.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.collections.domain.model.CollectionsManager
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.core.util.ScreenKey
import gg.jrg.audiminder.core.util.logChanges
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteCollectionsBottomSheetViewModel @Inject constructor(
    collectionsUseCases: CollectionsUseCases
) : ViewModel() {

    private var albumCollectionWithAlbums: AlbumCollectionWithAlbums? = null

    val collectionsList = CollectionsManager(
        collectionsUseCases,
        ScreenKey.COLLECTIONS_SCREEN
    ).apply { logChanges("DeleteCollectionsBottomSheetViewModel _collectionsList") }

    fun setAlbumCollectionWithAlbums(albumCollectionWithAlbums: AlbumCollectionWithAlbums) {
        this.albumCollectionWithAlbums = albumCollectionWithAlbums
    }

    fun getAlbumCollectionWithAlbums(): AlbumCollectionWithAlbums? = albumCollectionWithAlbums

    fun deleteAlbumCollection(): Job =
        viewModelScope.launch {
            albumCollectionWithAlbums?.let {
                collectionsList.deleteAlbumCollection(it.collection)
            }
        }


}