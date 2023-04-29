package gg.jrg.audiminder.music_services.data

sealed class MusicServiceAuthorizationState {
    object Unauthorized : MusicServiceAuthorizationState()
    object Authorized : MusicServiceAuthorizationState()
}