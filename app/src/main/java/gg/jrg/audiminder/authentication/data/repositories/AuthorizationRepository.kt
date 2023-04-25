package gg.jrg.audiminder.authentication.data.repositories

import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.data.services.AuthorizationService
import gg.jrg.audiminder.authentication.data.source.SupportedAuthorizationServiceDao
import gg.jrg.audiminder.authentication.di.AuthorizationServiceMap
import gg.jrg.audiminder.core.util.logChanges
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


interface AuthorizationRepository {
    fun getAuthorizationService(type: AuthServiceType): AuthorizationService
    fun refreshAuthorizationState(type: AuthServiceType)

    fun getAuthorizationState(type: AuthServiceType): StateFlow<AuthorizationState>

    suspend fun authorize(type: AuthServiceType)

    val areTheAuthorizationServicesInitialized: StateFlow<Boolean>
}

class AuthorizationRepositoryImpl @Inject constructor(
    private val supportedAuthorizationServiceDao: SupportedAuthorizationServiceDao,
    @AuthorizationServiceMap private val servicesMap: Map<String, @JvmSuppressWildcards AuthorizationService>
) : AuthorizationRepository {

    private val _authorizationServices =
        MutableStateFlow<Map<AuthServiceType, AuthorizationService>>(emptyMap()).apply { logChanges() }

    override val areTheAuthorizationServicesInitialized: StateFlow<Boolean> =
        _authorizationServices.map { authorizationServices ->
            authorizationServices.isNotEmpty()
        }.stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.WhileSubscribed(), false)

    init {
        val stackTrace = Thread.currentThread().stackTrace
        val callerIndex =
            stackTrace.indexOfFirst { it.className == AuthorizationRepositoryImpl::class.java.name } + 2
        val callerStackTraceElement = stackTrace.getOrNull(callerIndex)

        if (callerStackTraceElement != null) {
            Timber.d("Called from: $callerStackTraceElement")
        } else {
            Timber.d("Caller not found")
        }

        initAuthorizationServices()
    }

    private fun initAuthorizationServices() {
        CoroutineScope(Dispatchers.IO).launch {
            supportedAuthorizationServiceDao.getAllSupportedAuthorizationServices()
                .map { supportedServices ->
                    supportedServices.mapNotNull { dto ->
                        val authServiceType =
                            AuthServiceType.values().find { it.serviceAsString == dto.serviceName }

                        authServiceType?.let { type ->
                            val service = servicesMap[type.serviceAsString]
                            service?.let { type to service }
                        }
                    }.toMap()
                }
                .collect { servicesMap ->
                    _authorizationServices.value = servicesMap
                }
        }
    }

    override fun getAuthorizationService(type: AuthServiceType): AuthorizationService {
        return _authorizationServices.value[type]
            ?: throw IllegalArgumentException("No service found for type $type")
    }

    override fun refreshAuthorizationState(type: AuthServiceType) {
        return getAuthorizationService(type).refreshAuthorizationState()
    }

    override fun getAuthorizationState(type: AuthServiceType): StateFlow<AuthorizationState> {
        return getAuthorizationService(type).authorizationState
    }

    override suspend fun authorize(type: AuthServiceType) {
        getAuthorizationService(type).authorize()
    }

}
