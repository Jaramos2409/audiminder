package gg.jrg.audiminder.music_services.data.services

import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import kotlinx.coroutines.flow.StateFlow

interface MusicServiceProvider {
    val authorizationState: StateFlow<MusicServiceAuthorizationState>

    suspend fun authorize()

    suspend fun unauthorize()

    fun refreshAuthorizationState()

}