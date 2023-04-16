package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.authentication.data.services.AuthorizationService

class GetAuthorizationService(private val authorizationRepository: AuthorizationRepository) {

    operator fun invoke(type: AuthServiceType): AuthorizationService? {
        return authorizationRepository.getAuthorizationService(type)
    }

}