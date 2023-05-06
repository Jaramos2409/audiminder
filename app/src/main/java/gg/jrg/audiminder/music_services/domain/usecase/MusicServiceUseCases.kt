package gg.jrg.audiminder.music_services.domain.usecase

import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository
import javax.inject.Inject


class MusicServiceUseCases @Inject constructor(
    musicServiceRepository: MusicServiceRepository
) {
    val authorizeMusicServiceUseCase = AuthorizeMusicServiceSuspendUseCase(musicServiceRepository)
    val getMusicServiceProviderUseCase =
        GetMusicServiceProviderSuspendUseCase(musicServiceRepository)
    val getMusicServiceStateUseCase = GetMusicServiceStateUseCase(musicServiceRepository)
    val refreshMusicServiceAuthorizationStateUseCase =
        RefreshMusicServiceAuthorizationStateSuspendUseCase(musicServiceRepository)
    val unauthorizeMusicServiceUseCase = UnauthorizeMusicServiceUseCase(musicServiceRepository)
}

