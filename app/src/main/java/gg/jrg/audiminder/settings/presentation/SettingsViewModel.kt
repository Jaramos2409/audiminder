package gg.jrg.audiminder.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyUserDetailsUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SettingsViewModel @Inject constructor(
    spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases,
    spotifyUserDetailsUseCases: SpotifyUserDetailsUseCases
) : ViewModel() {

    private val _spotifyAuthorizationManager =
        SpotifyAuthorizationManager(spotifyAuthorizationUseCases)

    private val _isSpotifyAuthorized = combine(_spotifyAuthorizationManager) {
        _spotifyAuthorizationManager.isAuthorized()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _displayName = spotifyUserDetailsUseCases.getDisplayNameStateFlowUseCase()

    private val _profileImageFilePath =
        spotifyUserDetailsUseCases.getProfileImageFilePathUseCase()

    val shouldShowSpotifyAuthenticatedCard: Flow<Triple<Boolean, String, String>> =
        _isSpotifyAuthorized.flatMapLatest { shouldShow ->
            if (shouldShow) {
                combine(_displayName, _profileImageFilePath) { name, profilePath ->
                    Triple(shouldShow, name, profilePath)
                }
            } else {
                flowOf(Triple(false, "", ""))
            }
        }

    fun unauthorizeSpotify() {
        viewModelScope.launch {
            _spotifyAuthorizationManager.unauthorize()
        }
    }
}