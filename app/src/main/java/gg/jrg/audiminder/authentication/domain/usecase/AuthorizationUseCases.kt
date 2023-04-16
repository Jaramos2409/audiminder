package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository

data class AuthorizationUseCases(
    val getAuthorizationState: GetAuthorizationState,
    val authorizeService: AuthorizeService,
    val getAuthorizationService: GetAuthorizationService
) {
    constructor(authorizationRepository: AuthorizationRepository) : this(
        getAuthorizationService = GetAuthorizationService(authorizationRepository),
        getAuthorizationState = GetAuthorizationState(authorizationRepository),
        authorizeService = AuthorizeService(authorizationRepository)
    )
}
