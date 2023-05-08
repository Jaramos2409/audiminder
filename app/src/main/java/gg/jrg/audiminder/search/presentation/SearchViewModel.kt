package gg.jrg.audiminder.search.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases
) : ViewModel() {

    private val _spotifyAuthorizationManager =
        SpotifyAuthorizationManager(
            spotifyAuthorizationUseCases
        )

    fun isSpotifyAuthorized(): Boolean {
        return _spotifyAuthorizationManager.isAuthorized()
    }

}