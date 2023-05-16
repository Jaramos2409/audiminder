package gg.jrg.audiminder.music_services.domain.usecase.spotify.authorization

import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository
import kotlinx.coroutines.flow.StateFlow

class GetSpotifyAuthorizationStateUseCase(private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository) :
    UseCase<NoInput, StateFlow<MusicServiceAuthorizationState>> {
    override fun invoke(input: NoInput): StateFlow<MusicServiceAuthorizationState> {
        return spotifyAuthorizationRepository.authorizationState
    }
}