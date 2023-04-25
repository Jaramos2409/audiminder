package gg.jrg.audiminder.authentication.data.services

import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.auth.pkce.startSpotifyClientPkceLoginActivity
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.presentation.SpotifyPkceLoginActivityImpl
import gg.jrg.audiminder.core.util.ActivityStateFlowWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SpotifyAuthorizationService @Inject constructor(
    activityStateFlowWrapper: ActivityStateFlowWrapper,
    private val spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore,
) : AuthorizationService {

    override val authorizationState =
        MutableStateFlow<AuthorizationState>(AuthorizationState.Unauthorized)

    private val activityStateFlow = activityStateFlowWrapper.stateFlow


    init {
        refreshAuthorizationState()
    }

    override suspend fun authorize() {
        val activity = activityStateFlow.value
            ?: throw IllegalStateException("Activity is not available")
        activity.startSpotifyClientPkceLoginActivity(SpotifyPkceLoginActivityImpl::class.java)
    }

    override fun refreshAuthorizationState() {
        if (!(spotifyDefaultCredentialStore.spotifyAccessToken.isNullOrEmpty())) {
            authorizationState.value = AuthorizationState.Authorized
        } else {
            authorizationState.value = AuthorizationState.Unauthorized
        }
    }

}
