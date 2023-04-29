package gg.jrg.audiminder.music_services.domain.usecase

import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository
import kotlinx.coroutines.flow.StateFlow

class AreMusicServicesInitializedUseCase(private val musicServiceRepository: MusicServiceRepository) :
    UseCase<NoInput, StateFlow<Boolean>> {
    override suspend operator fun invoke(input: NoInput) =
        musicServiceRepository.areMusicServicesInitialized
}