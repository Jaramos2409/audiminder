package gg.jrg.audiminder.music_services.domain.usecase.spotify.authorization

import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository
import kotlinx.coroutines.flow.MutableStateFlow

class GetSpotifyAuthorizationStateUseCase(private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository) :
    UseCase<NoInput, MutableStateFlow<MusicServiceAuthorizationState>> {
    override fun invoke(input: NoInput): MutableStateFlow<MusicServiceAuthorizationState> {
        return spotifyAuthorizationRepository.authorizationState
    }
}