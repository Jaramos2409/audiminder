package gg.jrg.audiminder.music_services.domain.usecase.spotify

import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository
import gg.jrg.audiminder.music_services.domain.usecase.spotify.user_details.GetDisplayNameStateFlowUseCase
import gg.jrg.audiminder.music_services.domain.usecase.spotify.user_details.RefreshDisplayNameSuspendUseCase
import javax.inject.Inject

class SpotifyUserDetailsUseCases @Inject constructor(
    spotifyRepository: SpotifyRepository
) {
    val getDisplayNameStateFlowUseCase = GetDisplayNameStateFlowUseCase(spotifyRepository)
    val refreshDisplayNameSuspendUseCase = RefreshDisplayNameSuspendUseCase(spotifyRepository)
}