package gg.jrg.audiminder.music_services.domain.usecase.spotify.user_details

import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase
import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository

class RefreshDisplayNameSuspendUseCase(
    private val spotifyRepository: SpotifyRepository
) : SuspendUseCase<NoInput, Unit> {
    override suspend operator fun invoke(input: NoInput) =
        spotifyRepository.refreshDisplayName()
}