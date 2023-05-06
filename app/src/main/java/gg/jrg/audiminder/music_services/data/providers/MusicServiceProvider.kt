package gg.jrg.audiminder.music_services.data.providers

import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import kotlinx.coroutines.flow.MutableStateFlow

interface MusicServiceProvider {
    val authorizationState: MutableStateFlow<MusicServiceAuthorizationState>

    fun authorize()

    fun unauthorize()

    fun refreshAuthorizationState()
}