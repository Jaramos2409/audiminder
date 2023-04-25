package gg.jrg.audiminder.authentication.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.domain.usecase.AuthorizationUseCases
import gg.jrg.audiminder.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCases: AuthorizationUseCases
) : BaseViewModel() {

    private val _spotifyAuthorizationState =
        MutableStateFlow<AuthorizationState>(AuthorizationState.Unauthorized)

    init {
        viewModelScope.launch {
            val areAuthorizationServicesInitialized =
                authorizationUseCases.areAuthorizationServicesInitializedUseCase()

            if (areAuthorizationServicesInitialized.value) {
                fetchAuthorizationStateOfThisService(AuthServiceType.SPOTIFY)
            } else {
                areAuthorizationServicesInitialized.collect { isInitialized ->
                    if (isInitialized) {
                        fetchAuthorizationStateOfThisService(AuthServiceType.SPOTIFY)
                    }
                }
            }
        }
    }

    private fun fetchAuthorizationStateOfThisService(type: AuthServiceType) {
        viewModelScope.launch {
            refreshAuthorizationStateForThisService(type)

            authorizationUseCases.getAuthorizationStateUseCase(type)
                .collect { state -> _spotifyAuthorizationState.value = state }
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

    fun getAuthorizationStateOfThisService(type: AuthServiceType): AuthorizationState {
        return when (type) {
            AuthServiceType.SPOTIFY -> _spotifyAuthorizationState.value
            else -> throw IllegalArgumentException("Unknown AuthServiceType: $type")
        }
    }

}