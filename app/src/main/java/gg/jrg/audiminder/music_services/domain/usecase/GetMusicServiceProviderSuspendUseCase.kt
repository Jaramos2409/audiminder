package gg.jrg.audiminder.music_services.domain.usecase

import gg.jrg.audiminder.core.presentation.usecase.SuspendUseCase
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.providers.MusicServiceProvider
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository

class GetMusicServiceProviderSuspendUseCase(private val musicServiceRepository: MusicServiceRepository) :
    SuspendUseCase<MusicServiceType, MusicServiceProvider> {
    override suspend operator fun invoke(input: MusicServiceType): MusicServiceProvider =
        musicServiceRepository.getMusicServiceProvider(input)
}