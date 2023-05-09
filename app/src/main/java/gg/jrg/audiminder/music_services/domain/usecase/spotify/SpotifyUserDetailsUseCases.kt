package gg.jrg.audiminder.music_services.domain.usecase.spotify

import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository
import gg.jrg.audiminder.music_services.domain.usecase.spotify.user_details.GetDisplayNameStateFlowUseCase
import gg.jrg.audiminder.music_services.domain.usecase.spotify.user_details.GetProfileImageFilePathUseCase
import gg.jrg.audiminder.music_services.domain.usecase.spotify.user_details.RefreshUserDataSuspendUseCase
import javax.inject.Inject

class SpotifyUserDetailsUseCases @Inject constructor(
    spotifyRepository: SpotifyRepository
) {
    val getDisplayNameStateFlowUseCase = GetDisplayNameStateFlowUseCase(spotifyRepository)
    val refreshUserDataSuspendUseCase = RefreshUserDataSuspendUseCase(spotifyRepository)
    val getProfileImageFilePathUseCase = GetProfileImageFilePathUseCase(spotifyRepository)
}