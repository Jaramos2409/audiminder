package gg.jrg.audiminder.music_services.domain.usecase.spotify.authorization

import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository

class RefreshSpotifyAuthorizationStateUseCase(private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository) :
    UseCase<NoInput, Unit> {
    override operator fun invoke(input: NoInput) =
        spotifyAuthorizationRepository.refreshAuthorizationState()
}
