package gg.jrg.audiminder.authentication.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.data.services.AuthorizationService
import gg.jrg.audiminder.authentication.domain.usecase.AuthorizationUseCases
import gg.jrg.audiminder.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCases: AuthorizationUseCases
) : BaseViewModel() {

    fun getAuthorizationService(type: AuthServiceType): AuthorizationService? {
        return authorizationUseCases.getAuthorizationService(type)
    }

    fun getAuthorizationState(service: AuthorizationService): SharedFlow<AuthorizationState> {
        return authorizationUseCases.getAuthorizationState(service)
    }

    fun authorize(service: AuthorizationService) {
        viewModelScope.launch {
            authorizationUseCases.authorizeService(service)
        }
    }

}