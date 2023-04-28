package gg.jrg.audiminder.authentication.data.services

import gg.jrg.audiminder.authentication.data.AuthorizationState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationService {
    val authorizationState: StateFlow<AuthorizationState>

    suspend fun authorize()

    suspend fun unauthorize()
    fun refreshAuthorizationState()
}