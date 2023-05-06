package gg.jrg.audiminder.music_services.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.domain.model.MusicServiceAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.MusicServiceUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyPkceLoginViewModel @Inject constructor(
    musicServiceUseCases: MusicServiceUseCases
) : ViewModel() {

    private val _spotifyAuthorizationManager =
        MusicServiceAuthorizationManager(musicServiceUseCases, MusicServiceType.SPOTIFY)

    fun refreshAuthorizationStateForSpotify() {
        viewModelScope.launch {
            _spotifyAuthorizationManager.refreshAuthorizationState()
        }
    }
}