package gg.jrg.audiminder.authentication.presentation

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.SpotifyScope
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.auth.pkce.AbstractSpotifyPkceLoginActivity
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.BuildConfig
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.core.presentation.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class SpotifyPkceLoginActivityImpl : AbstractSpotifyPkceLoginActivity() {

    @Inject
    lateinit var spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore
    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    override val clientId = BuildConfig.SPOTIFY_CLIENT_ID
    override val redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI
    override val scopes = SpotifyScope.values().toList()

    override fun onSuccess(api: SpotifyClientApi) {
        spotifyDefaultCredentialStore.setSpotifyApi(api)

        authorizationViewModel.refreshAuthorizationStateForThisService(AuthServiceType.SPOTIFY)

        val classBackTo = MainActivity::class.java
        Toast.makeText(
            this,
            "Authentication via PKCE has completed. Launching ${classBackTo.simpleName}..",
            Toast.LENGTH_SHORT
        ).show()
        startActivity(Intent(this, classBackTo))
    }

    override fun onFailure(exception: Exception) {
        exception.printStackTrace()
        Toast.makeText(
            this,
            "Auth failed: ${exception.message}",
            Toast.LENGTH_SHORT
        ).show()
    }
}