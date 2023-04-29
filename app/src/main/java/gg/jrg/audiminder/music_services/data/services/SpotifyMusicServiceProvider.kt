package gg.jrg.audiminder.music_services.data.services

import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.auth.pkce.startSpotifyClientPkceLoginActivity
import gg.jrg.audiminder.core.util.ActivityStateFlowWrapper
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.presentation.SpotifyPkceLoginActivityImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SpotifyMusicServiceProvider @Inject constructor(
    activityStateFlowWrapper: ActivityStateFlowWrapper,
    private val spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore,
) : MusicServiceProvider {

    override val authorizationState =
        MutableStateFlow<MusicServiceAuthorizationState>(MusicServiceAuthorizationState.Unauthorized)

    private val activityStateFlow = activityStateFlowWrapper.stateFlow

    init {
        refreshAuthorizationState()
    }

    override suspend fun authorize() {
        val activity = activityStateFlow.value
            ?: throw IllegalStateException("Activity is not available")
        activity.startSpotifyClientPkceLoginActivity(SpotifyPkceLoginActivityImpl::class.java)
    }

    override suspend fun unauthorize() {
        spotifyDefaultCredentialStore.spotifyAccessToken = null
    }

    override fun refreshAuthorizationState() {
        if (!(spotifyDefaultCredentialStore.spotifyAccessToken.isNullOrEmpty())) {
            authorizationState.value = MusicServiceAuthorizationState.Authorized
        } else {
            authorizationState.value = MusicServiceAuthorizationState.Unauthorized
        }
    }

}
