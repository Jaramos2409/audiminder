package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.core.presentation.usecase.NoInput
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import kotlinx.coroutines.flow.StateFlow

class AreAuthorizationServicesInitializedUseCase(private val authorizationRepository: AuthorizationRepository) :
    UseCase<NoInput, StateFlow<Boolean>> {
    override suspend operator fun invoke(input: NoInput) =
        authorizationRepository.areTheAuthorizationServicesInitialized
}