package gg.jrg.audiminder.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyUserDetailsUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases,
    spotifyUserDetailsUseCases: SpotifyUserDetailsUseCases
) : ViewModel() {

    private val _displayName = spotifyUserDetailsUseCases.getDisplayNameStateFlowUseCase()

    private val _welcomeText = _displayName.map { displayName ->
        if (displayName.isEmpty()) return@map ("Welcome!")
        "Welcome, $displayName!"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "Welcome!")
    val welcomeText: StateFlow<String>
        get() = _welcomeText

    private val _spotifyAuthorizationManager =
        SpotifyAuthorizationManager(
            spotifyAuthorizationUseCases
        )

    init {
        viewModelScope.launch {
            if (isSpotifyAuthorized()) {
                spotifyUserDetailsUseCases.refreshDisplayNameSuspendUseCase()
            }
        }
    }

    fun isSpotifyAuthorized(): Boolean {
        return _spotifyAuthorizationManager.isAuthorized()
    }

}