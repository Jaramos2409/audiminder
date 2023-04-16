package gg.jrg.audiminder.authentication.data.services

import gg.jrg.audiminder.authentication.data.AuthorizationState
import kotlinx.coroutines.flow.SharedFlow

interface AuthorizationService {
    val authorizationState: SharedFlow<AuthorizationState>
    suspend fun authorize()
    fun checkAuthorization(): Boolean
}