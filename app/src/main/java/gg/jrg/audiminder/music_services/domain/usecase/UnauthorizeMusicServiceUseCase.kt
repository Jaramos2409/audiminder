package gg.jrg.audiminder.music_services.domain.usecase

import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository

class UnauthorizeMusicServiceUseCase(private val musicServiceRepository: MusicServiceRepository) :
    UseCase<MusicServiceType, Unit> {
    override operator fun invoke(input: MusicServiceType) =
        musicServiceRepository.unauthorize(input)
}