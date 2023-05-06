package gg.jrg.audiminder.music_services.domain.usecase

import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository

class RefreshMusicServiceAuthorizationStateSuspendUseCase(private val musicServiceRepository: MusicServiceRepository) :
    SuspendUseCase<MusicServiceType, Unit> {
    override suspend operator fun invoke(input: MusicServiceType) =
        musicServiceRepository.refreshAuthorizationState(input)
}
