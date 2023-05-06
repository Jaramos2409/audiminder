package gg.jrg.audiminder.search.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.domain.model.MusicServiceAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.MusicServiceUseCases
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    musicServiceUseCases: MusicServiceUseCases
) : ViewModel() {

    private val _spotifyAuthorizationManager =
        MusicServiceAuthorizationManager(
            musicServiceUseCases,
            MusicServiceType.SPOTIFY
        )

    fun isSpotifyAuthorized(): Boolean {
        return _spotifyAuthorizationManager.isAuthorized()
    }

}