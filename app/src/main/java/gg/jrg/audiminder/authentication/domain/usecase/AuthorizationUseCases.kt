package gg.jrg.audiminder.authentication.domain.usecase

import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import javax.inject.Inject


class AuthorizationUseCases @Inject constructor(
    authorizationRepository: AuthorizationRepository
) {
    val authorizeUseCase = AuthorizeUseCase(authorizationRepository)
    val getAuthorizationServiceUseCase = GetAuthorizationServiceUseCase(authorizationRepository)
    val getAuthorizationStateUseCase = GetAuthorizationStateUseCase(authorizationRepository)
    val refreshAuthorizationStateUseCase = RefreshAuthorizationStateUseCase(authorizationRepository)
    val areAuthorizationServicesInitializedUseCase =
        AreAuthorizationServicesInitializedUseCase(authorizationRepository)
    val unauthorizeUseCase = UnauthorizeUseCase(authorizationRepository)
}

