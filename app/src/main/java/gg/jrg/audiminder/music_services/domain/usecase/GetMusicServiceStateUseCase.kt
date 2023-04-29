package gg.jrg.audiminder.music_services.domain.usecase

import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository
import kotlinx.coroutines.flow.SharedFlow

class GetMusicServiceStateUseCase(private val musicServiceRepository: MusicServiceRepository) :
    UseCase<MusicServiceType, SharedFlow<MusicServiceAuthorizationState>> {
    override suspend fun invoke(input: MusicServiceType): SharedFlow<MusicServiceAuthorizationState> {
        return musicServiceRepository.getAuthorizationState(input)
    }
}