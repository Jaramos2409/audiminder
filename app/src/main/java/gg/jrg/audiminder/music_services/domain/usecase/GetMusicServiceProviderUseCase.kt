package gg.jrg.audiminder.music_services.domain.usecase

import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository
import gg.jrg.audiminder.music_services.data.services.MusicServiceProvider

class GetMusicServiceProviderUseCase(private val musicServiceRepository: MusicServiceRepository) :
    UseCase<MusicServiceType, MusicServiceProvider> {
    override suspend operator fun invoke(input: MusicServiceType): MusicServiceProvider =
        musicServiceRepository.getMusicServiceProvider(input)
}