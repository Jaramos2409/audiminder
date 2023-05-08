package gg.jrg.audiminder.music_services.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizeMusicServiceViewModel @Inject constructor(
    spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases
) : ViewModel() {

    private val _spotifyAuthorizationManager =
        SpotifyAuthorizationManager(spotifyAuthorizationUseCases)

    fun authorizeSpotify() {
        viewModelScope.launch {
            _spotifyAuthorizationManager.authorize()
        }
    }
}