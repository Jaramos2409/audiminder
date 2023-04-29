package gg.jrg.audiminder.music_services.domain.usecase

import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository
import javax.inject.Inject


class MusicServiceUseCases @Inject constructor(
    musicServiceRepository: MusicServiceRepository
) {
    val authorizeMusicServiceUseCase = AuthorizeMusicServiceUseCase(musicServiceRepository)
    val getMusicServiceProviderUseCase = GetMusicServiceProviderUseCase(musicServiceRepository)
    val getMusicServiceStateUseCase = GetMusicServiceStateUseCase(musicServiceRepository)
    val refreshMusicServiceAuthorizationStateUseCase =
        RefreshMusicServiceAuthorizationStateUseCase(musicServiceRepository)
    val areMusicServicesInitializedUseCase =
        AreMusicServicesInitializedUseCase(musicServiceRepository)
    val unauthorizeMusicServiceUseCase = UnauthorizeMusicServiceUseCase(musicServiceRepository)
}

