package gg.jrg.audiminder.collections.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.collections.domain.model.CollectionsManager
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.core.util.ScreenKey
import gg.jrg.audiminder.core.util.logChanges
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyUserDetailsUseCases
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

    val collectionsList = CollectionsManager(
        collectionsUseCases,
        ScreenKey.COLLECTIONS_SCREEN
    ).apply { logChanges("CollectionsViewModel _collectionsList") }

    init {
        viewModelScope.launch {
            if (_spotifyAuthorizationManager.isAuthorized()) {
                spotifyUserDetailsUseCases.refreshUserDataSuspendUseCase()
            }
            refreshListOfCollections()
        }
    }

    fun refreshListOfCollections() {
        viewModelScope.launch {
            collectionsList.refreshListOfCollections()
        }
    }

}