package gg.jrg.audiminder.music_services.domain.usecase.spotify.user_details

import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository
import kotlinx.coroutines.flow.StateFlow

class GetDisplayNameStateFlowUseCase(
    private val spotifyRepository: SpotifyRepository
) : UseCase<NoInput, StateFlow<String>> {
    override operator fun invoke(input: NoInput) =
        spotifyRepository.displayName
}