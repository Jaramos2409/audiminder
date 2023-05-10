package gg.jrg.audiminder.music_services.domain.usecase.spotify.search

import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase
import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository
import kotlinx.coroutines.flow.Flow

class SearchSpotifyAndFetchResultsSuspendUseCase(private val spotifyRepository: SpotifyRepository) :
    SuspendUseCase<String, Flow<List<Album>>> {
    override suspend operator fun invoke(input: String): Flow<List<Album>> {
        return spotifyRepository.searchSpotifyAndFetchAlbums(input)
    }
}