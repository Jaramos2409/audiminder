package gg.jrg.audiminder.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases
) : ViewModel() {

    private val _spotifyAuthorizationManager =
        SpotifyAuthorizationManager(spotifyAuthorizationUseCases)

    private val _shouldShowUnauthenticateButton = combine(_spotifyAuthorizationManager) {
        _spotifyAuthorizationManager.isAuthorized()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val shouldShowUnauthenticateButton: StateFlow<Boolean>
        get() = _shouldShowUnauthenticateButton

    fun isSpotifyAuthorized(): Boolean {
        return _spotifyAuthorizationManager.isAuthorized()
    }

    fun unauthorizeSpotify() {
        viewModelScope.launch {
            _spotifyAuthorizationManager.unauthorize()
        }
    }
}