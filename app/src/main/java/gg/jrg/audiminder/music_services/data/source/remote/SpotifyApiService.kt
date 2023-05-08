package gg.jrg.audiminder.music_services.data.source.remote

import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.SpotifyException
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.models.SpotifyUserInformation
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface SpotifyApiService {
    fun getUserData(): SpotifyUserInformation?
}

class SpotifyApiServiceImpl @Inject constructor(
    private val spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore,
    private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository
) : SpotifyApiService {

    private fun <T> guardValidSpotifyApi(
        alreadyTriedToReauthenticate: Boolean = false,
        block: suspend (api: SpotifyClientApi) -> T?
    ): T? {
        return runBlocking {
            try {
                spotifyDefaultCredentialStore.getSpotifyClientPkceApi()?.let { block(it) }
            } catch (e: SpotifyException) {
                e.printStackTrace()
                val api = spotifyDefaultCredentialStore.getSpotifyClientPkceApi()!!
                if (!alreadyTriedToReauthenticate) {
                    try {
                        api.refreshToken()
                        spotifyDefaultCredentialStore.spotifyToken = api.token
                        block(api)
                    } catch (e: SpotifyException.ReAuthenticationNeededException) {
                        e.printStackTrace()
                        return@runBlocking guardValidSpotifyApi(
                            alreadyTriedToReauthenticate = true,
                            block = block
                        )
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                        return@runBlocking guardValidSpotifyApi(
                            alreadyTriedToReauthenticate = true,
                            block = block
                        )
                    }
                } else {
                    spotifyAuthorizationRepository.authorize()
                    null
                }
            }
        }
    }

    override fun getUserData(): SpotifyUserInformation? {
        return guardValidSpotifyApi { api ->
            return@guardValidSpotifyApi api.users.getClientProfile()
        }
    }

}