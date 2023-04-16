package gg.jrg.audiminder.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.data.services.AuthorizationService
import gg.jrg.audiminder.authentication.domain.usecase.AuthorizationUseCases
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authorizationUseCases: AuthorizationUseCases
) : ViewModel() {

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
