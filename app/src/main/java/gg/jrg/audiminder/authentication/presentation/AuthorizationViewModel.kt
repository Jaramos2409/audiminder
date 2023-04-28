package gg.jrg.audiminder.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.domain.usecase.AuthorizationUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCases: AuthorizationUseCases
) : ViewModel() {

    private val _spotifyAuthorizationState =
        MutableStateFlow<AuthorizationState>(AuthorizationState.Unauthorized)

    init {
        viewModelScope.launch {
            val areAuthorizationServicesInitialized =
                authorizationUseCases.areAuthorizationServicesInitializedUseCase()

            if (areAuthorizationServicesInitialized.value) {
                fetchAndUpdateAuthorizationStateOfThisService(AuthServiceType.SPOTIFY)
            } else {
                areAuthorizationServicesInitialized.collect { isInitialized ->
                    if (isInitialized) {
                        fetchAndUpdateAuthorizationStateOfThisService(AuthServiceType.SPOTIFY)
                    }
                }
            }
        }
    }

    private fun fetchAndUpdateAuthorizationStateOfThisService(type: AuthServiceType) {
        viewModelScope.launch {
            refreshAuthorizationStateForThisService(type)

            authorizationUseCases.getAuthorizationStateUseCase(type)
                .collect { state ->
                    run {
                        returnAuthorizationStateFlowBasedOnType(type).value = state
                    }
                }
        }
    }

    fun authorizeThisService(type: AuthServiceType) {
        viewModelScope.launch {
            authorizationUseCases.authorizeUseCase(type)
        }
    }

    fun refreshAuthorizationStateForThisService(type: AuthServiceType) {
        viewModelScope.launch {
            authorizationUseCases.refreshAuthorizationStateUseCase(type)
        }
    }

    fun isThisServiceAuthorized(type: AuthServiceType): Boolean {
        return returnAuthorizationStateFlowBasedOnType(type).value == AuthorizationState.Authorized
    }

    fun unauthorizeThisService(type: AuthServiceType) {
        viewModelScope.launch {
            authorizationUseCases.unauthorizeUseCase(type)
        }
    }

    private fun returnAuthorizationStateFlowBasedOnType(type: AuthServiceType) =
        when (type) {
            AuthServiceType.SPOTIFY -> _spotifyAuthorizationState
            else -> throw IllegalArgumentException("Unknown AuthServiceType: $type")
        }

}