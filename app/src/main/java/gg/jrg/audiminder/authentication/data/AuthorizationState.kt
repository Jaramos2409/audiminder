package gg.jrg.audiminder.authentication.data

sealed class AuthorizationState {
    object Unauthorized : AuthorizationState()
    object Authorized : AuthorizationState()
}