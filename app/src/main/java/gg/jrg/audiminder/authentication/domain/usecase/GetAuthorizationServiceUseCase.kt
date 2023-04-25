package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.authentication.data.services.AuthorizationService
import gg.jrg.audiminder.core.presentation.usecase.UseCase

class GetAuthorizationServiceUseCase(private val authorizationRepository: AuthorizationRepository) :
    UseCase<AuthServiceType, AuthorizationService> {
    override suspend operator fun invoke(input: AuthServiceType): AuthorizationService =
        authorizationRepository.getAuthorizationService(input)
}