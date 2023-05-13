package gg.jrg.audiminder.collections.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyUserDetailsUseCases
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases,
    spotifyUserDetailsUseCases: SpotifyUserDetailsUseCases,
    collectionsUseCases: CollectionsUseCases
) : ViewModel() {

    private val _spotifyAuthorizationManager =
        SpotifyAuthorizationManager(
            spotifyAuthorizationUseCases
        )

    private val _collectionsList = collectionsUseCases.getCollectionsStateFlowUseCase()
    val collectionsList: StateFlow<List<AlbumCollection>>
        get() = _collectionsList

    init {
        viewModelScope.launch {
            if (isSpotifyAuthorized()) {
                spotifyUserDetailsUseCases.refreshUserDataSuspendUseCase()
            }
            collectionsUseCases.refreshListOfCollectionsSuspendUseCase()
        }
    }

    fun isSpotifyAuthorized(): Boolean {
        return _spotifyAuthorizationManager.isAuthorized()
    }

}