package gg.jrg.audiminder.music_services.data.repositories

import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.auth.pkce.startSpotifyClientPkceLoginActivity
import gg.jrg.audiminder.core.util.ActivityStateFlowWrapper
import gg.jrg.audiminder.core.util.logChanges
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.presentation.SpotifyPkceLoginActivityImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface SpotifyAuthorizationRepository {
    val authorizationState: MutableStateFlow<MusicServiceAuthorizationState>

    fun authorize()

    fun unauthorize()

    fun refreshAuthorizationState()
}

class SpotifyAuthorizationRepositoryImpl @Inject constructor(
    activityStateFlowWrapper: ActivityStateFlowWrapper,
    private val spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore,
) : SpotifyAuthorizationRepository {

    override val authorizationState =
        MutableStateFlow<MusicServiceAuthorizationState>(MusicServiceAuthorizationState.Unauthorized).apply {
            logChanges(
                "SpotifyMusicServiceProvider authorizationState"
            )
        }

    private val activityStateFlow = activityStateFlowWrapper.stateFlow

    init {
        refreshAuthorizationState()
    }

    override fun authorize() {
        val activity = activityStateFlow.value
            ?: throw IllegalStateException("Activity is not available")
        activity.startSpotifyClientPkceLoginActivity(SpotifyPkceLoginActivityImpl::class.java)
    }

    override fun unauthorize() {
        spotifyDefaultCredentialStore.spotifyAccessToken = null
        refreshAuthorizationState()
    }

    override fun refreshAuthorizationState() {
        if (!(spotifyDefaultCredentialStore.spotifyAccessToken.isNullOrEmpty())) {
            authorizationState.value = MusicServiceAuthorizationState.Authorized
        } else {
            authorizationState.value = MusicServiceAuthorizationState.Unauthorized
        }
    }

}
