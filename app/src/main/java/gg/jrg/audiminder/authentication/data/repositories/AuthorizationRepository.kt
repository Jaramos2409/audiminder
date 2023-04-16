package gg.jrg.audiminder.authentication.data.repositories

import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.data.services.AuthorizationService
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject


interface AuthorizationRepository {
    fun getAuthorizationService(type: AuthServiceType): AuthorizationService?
    fun getAuthorizationState(service: AuthorizationService): SharedFlow<AuthorizationState>
    suspend fun authorize(service: AuthorizationService)
}

class AuthorizationRepositoryImpl @Inject constructor(
    private val authorizationServices: HashMap<AuthServiceType, AuthorizationService>
) : AuthorizationRepository {

    override fun getAuthorizationService(type: AuthServiceType): AuthorizationService? {
        return authorizationServices[type]
    }

    override fun getAuthorizationState(service: AuthorizationService): SharedFlow<AuthorizationState> {
        return service.authorizationState
    }

    override suspend fun authorize(service: AuthorizationService) {
        service.authorize()
    }

}
