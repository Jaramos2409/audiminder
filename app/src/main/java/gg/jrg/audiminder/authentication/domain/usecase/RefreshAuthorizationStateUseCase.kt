package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.core.presentation.usecase.UseCase

class RefreshAuthorizationStateUseCase(private val authorizationRepository: AuthorizationRepository) :
    UseCase<AuthServiceType, Unit> {
    override suspend operator fun invoke(input: AuthServiceType) =
        authorizationRepository.refreshAuthorizationState(input)
}
