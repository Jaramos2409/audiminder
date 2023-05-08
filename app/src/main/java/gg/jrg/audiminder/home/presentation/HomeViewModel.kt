package gg.jrg.audiminder.home.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases
) : ViewModel() {

    private val _welcomeText = MutableStateFlow("Welcome!")
    val welcomeText: StateFlow<String>
        get() = _welcomeText

    private val _spotifyAuthorizationManager =
        SpotifyAuthorizationManager(
            spotifyAuthorizationUseCases
        )

    init {
        if (isSpotifyAuthorized()) {

        }
    }

    fun isSpotifyAuthorized(): Boolean {
        return _spotifyAuthorizationManager.isAuthorized()
    }

}