package gg.jrg.audiminder.music_services.domain.model

import gg.jrg.audiminder.core.util.logAndReturnEvaluation
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.domain.usecase.MusicServiceUseCases

class MusicServiceAuthorizationManager(
    private val musicServiceUseCases: MusicServiceUseCases,
    private val musicServiceType: MusicServiceType
) {

    private var _authorizationState =
        musicServiceUseCases.getMusicServiceStateUseCase(musicServiceType)

    fun isAuthorized(): Boolean {
        return logAndReturnEvaluation(
            _authorizationState.value == MusicServiceAuthorizationState.Authorized
        )
    }

    suspend fun authorize() {
        musicServiceUseCases.authorizeMusicServiceUseCase(musicServiceType)
    }

    fun unauthorize() {
        musicServiceUseCases.unauthorizeMusicServiceUseCase(musicServiceType)
    }

    suspend fun refreshAuthorizationState() {
        musicServiceUseCases.refreshMusicServiceAuthorizationStateUseCase(musicServiceType)
    }
}
