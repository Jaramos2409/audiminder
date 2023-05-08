package gg.jrg.audiminder.music_services.domain.model

import gg.jrg.audiminder.core.util.logAndReturnEvaluation
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import kotlinx.coroutines.flow.MutableStateFlow

class SpotifyAuthorizationManager(
    private val spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases
) : MutableStateFlow<MusicServiceAuthorizationState> by spotifyAuthorizationUseCases.getSpotifyAuthorizationStateUseCase() {

    fun isAuthorized(): Boolean {
        return logAndReturnEvaluation(
            value == MusicServiceAuthorizationState.Authorized
        )
    }

    fun authorize() {
        spotifyAuthorizationUseCases.authorizeMusicServiceUseCase()
    }

    suspend fun unauthorize() {
        spotifyAuthorizationUseCases.unauthorizeSpotifySuspendUseCase()
    }

    fun refreshAuthorizationState() {
        spotifyAuthorizationUseCases.refreshMusicServiceAuthorizationStateUseCase()
    }
}
