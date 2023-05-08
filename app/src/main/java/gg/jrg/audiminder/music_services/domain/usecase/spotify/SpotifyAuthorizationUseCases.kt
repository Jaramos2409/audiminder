package gg.jrg.audiminder.music_services.domain.usecase.spotify

import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository
import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository
import gg.jrg.audiminder.music_services.domain.usecase.spotify.authorization.AuthorizeSpotifyUseCase
import gg.jrg.audiminder.music_services.domain.usecase.spotify.authorization.GetSpotifyAuthorizationStateUseCase
import gg.jrg.audiminder.music_services.domain.usecase.spotify.authorization.RefreshSpotifyAuthorizationStateUseCase
import gg.jrg.audiminder.music_services.domain.usecase.spotify.authorization.UnauthorizeSpotifySuspendUseCase
import javax.inject.Inject


class SpotifyAuthorizationUseCases @Inject constructor(
    spotifyAuthorizationRepository: SpotifyAuthorizationRepository,
    spotifyRepository: SpotifyRepository
) {
    val authorizeMusicServiceUseCase = AuthorizeSpotifyUseCase(spotifyAuthorizationRepository)
    val getSpotifyAuthorizationStateUseCase =
        GetSpotifyAuthorizationStateUseCase(spotifyAuthorizationRepository)
    val refreshMusicServiceAuthorizationStateUseCase =
        RefreshSpotifyAuthorizationStateUseCase(spotifyAuthorizationRepository)
    val unauthorizeSpotifySuspendUseCase =
        UnauthorizeSpotifySuspendUseCase(spotifyAuthorizationRepository, spotifyRepository)
}

