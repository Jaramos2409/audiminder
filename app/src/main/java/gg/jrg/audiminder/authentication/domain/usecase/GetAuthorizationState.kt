package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.authentication.data.services.AuthorizationService
import kotlinx.coroutines.flow.SharedFlow

class GetAuthorizationState(private val authorizationRepository: AuthorizationRepository) {

    operator fun invoke(service: AuthorizationService): SharedFlow<AuthorizationState> {
        return authorizationRepository.getAuthorizationState(service)
    }

}