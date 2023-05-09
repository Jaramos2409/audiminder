package gg.jrg.audiminder.music_services.domain.usecase.spotify.authorization

import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository
import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository

class UnauthorizeSpotifySuspendUseCase(
    private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository,
    private val spotifyRepository: SpotifyRepository
) :
    SuspendUseCase<NoInput, Unit> {
    override suspend operator fun invoke(input: NoInput) {
        spotifyAuthorizationRepository.unauthorize()
        spotifyRepository.clearDisplayName()
        spotifyRepository.clearProfileImageFilePath()
    }
}