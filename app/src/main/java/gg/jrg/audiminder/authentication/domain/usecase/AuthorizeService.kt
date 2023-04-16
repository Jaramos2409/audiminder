package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.authentication.data.services.AuthorizationService

class AuthorizeService(private val authorizationRepository: AuthorizationRepository) {

    suspend operator fun invoke(service: AuthorizationService) {
        authorizationRepository.authorize(service)
    }

}