package gg.jrg.audiminder.collections.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.core.util.logChanges
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewCollectionViewModel @Inject constructor(
    private val collectionsUseCases: CollectionsUseCases
) : ViewModel() {

    private var album: Album? = null

    private val _collectionName =
        MutableStateFlow("").apply { logChanges("CreateNewCollectionViewModel _collectionName") }

    fun setAlbum(album: Album) {
        this.album = album
    }

    fun getAlbum(): Album? {
        return album
    }

    fun saveNewCollection() {
        viewModelScope.launch {
            collectionsUseCases.saveCollectionSuspendUseCase(
                AlbumCollection(
                    name = _collectionName.value
                )
            )
        }
    }

    fun setCollectionName(name: String) {
        _collectionName.value = name
    }

}