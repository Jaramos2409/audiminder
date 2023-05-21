package gg.jrg.audiminder.collections.presentation.create_new_collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.collections.domain.usecase.AddAlbumToAlbumCollectionInputParameters
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.core.util.FlowValidator
import gg.jrg.audiminder.core.util.logChanges
import kotlinx.coroutines.Job
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

    private val collectionNameValidator = FlowValidator(_collectionName) { !it.isNullOrBlank() }

    private val _saveSuccessful = MutableStateFlow(false)

    fun setAlbum(album: Album) {
        this.album = album
    }

    fun getAlbum(): Album? {
        return album
    }

    fun saveNewCollection(): Job {
        return viewModelScope.launch {
            if (!collectionNameValidator.validate()) {
                _saveSuccessful.value = false
                return@launch
            }

            collectionsUseCases.saveCollectionSuspendUseCase(
                AlbumCollection(
                    name = _collectionName.value
                )
            ).let { collection ->
                album?.let { album ->
                    collectionsUseCases.addAlbumToAlbumCollectionSuspendUseCase(
                        AddAlbumToAlbumCollectionInputParameters(
                            album = album,
                            collection = collection
                        )
                    )
                }
            }

            _saveSuccessful.value = true
        }
    }

    fun setCollectionName(name: String) {
        _collectionName.value = name
    }

    fun checkIfSaveSuccessful(): Boolean {
        return _saveSuccessful.value
    }

}

