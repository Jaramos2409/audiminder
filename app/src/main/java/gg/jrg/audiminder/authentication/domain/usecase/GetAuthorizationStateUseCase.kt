package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.core.presentation.usecase.UseCase
import kotlinx.coroutines.flow.SharedFlow

class GetAuthorizationStateUseCase(private val authorizationRepository: AuthorizationRepository) :
    UseCase<AuthServiceType, SharedFlow<AuthorizationState>> {
    override suspend fun invoke(input: AuthServiceType): SharedFlow<AuthorizationState> {
        return authorizationRepository.getAuthorizationState(input)
    }
}