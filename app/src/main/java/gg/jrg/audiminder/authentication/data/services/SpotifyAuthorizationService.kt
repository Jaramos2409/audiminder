package gg.jrg.audiminder.authentication.data.services

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import gg.jrg.audiminder.BuildConfig
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.data.SecureSpotifyAuthorizationTokenStorage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class SpotifyAuthorizationService(
    private val context: Context,
    private val secureSpotifyAuthorizationTokenStorage: SecureSpotifyAuthorizationTokenStorage
) : AuthorizationService {
    private val _authorizationState = MutableSharedFlow<AuthorizationState>()
    override val authorizationState: SharedFlow<AuthorizationState>
        get() = _authorizationState

    override suspend fun authorize() {
        val request = AuthorizationRequest.Builder(
            BuildConfig.SPOTIFY_CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            BuildConfig.SPOTIFY_REDIRECT_URI
        )
            .setScopes(arrayOf("user-read-email"))
            .build()

        AuthorizationClient.openLoginActivity(
            context as AppCompatActivity,
            REQUEST_CODE_SPOTIFY_LOGIN,
            request
        )
    }

    override fun checkAuthorization(): Boolean =
        !(secureSpotifyAuthorizationTokenStorage.getAuthToken().isNullOrEmpty())

    fun onAuthorizationResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SPOTIFY_LOGIN) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    secureSpotifyAuthorizationTokenStorage.storeAuthToken(response.accessToken)
                    _authorizationState.tryEmit(AuthorizationState.Authorized)
                }
                else -> {
                    _authorizationState.tryEmit(AuthorizationState.Unauthorized)
                    throw java.lang.Exception(response.error)
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_SPOTIFY_LOGIN = 1337
    }
}
