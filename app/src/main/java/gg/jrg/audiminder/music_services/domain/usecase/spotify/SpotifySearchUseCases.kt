package gg.jrg.audiminder.music_services.domain.usecase.spotify

import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository
import gg.jrg.audiminder.music_services.domain.usecase.spotify.search.SearchSpotifyAndFetchResultsSuspendUseCase
import javax.inject.Inject

class SpotifySearchUseCases @Inject constructor(
    spotifyRepository: SpotifyRepository
) {
    val searchSpotifyAndFetchResultsSuspendUseCase =
        SearchSpotifyAndFetchResultsSuspendUseCase(spotifyRepository)
}