package gg.jrg.audiminder.music_services.data.source.remote

import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.SpotifyException
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.models.RecommendationResponse
import com.adamratzman.spotify.models.SpotifyUserInformation
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SpotifyApiService {
    suspend fun getUserData(): SpotifyUserInformation?
    suspend fun getRecommendations(): RecommendationResponse?
}

class SpotifyApiServiceImpl @Inject constructor(
    private val spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore,
    private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    override suspend fun getUserData(): SpotifyUserInformation? = withContext(ioDispatcher) {
        return@withContext guardValidSpotifyApi { api ->
            return@guardValidSpotifyApi api.users.getClientProfile()
        }
    }

    override suspend fun getRecommendations() = withContext(ioDispatcher) {
        return@withContext guardValidSpotifyApi { api ->
            return@guardValidSpotifyApi api.browse.getRecommendations()
        }
    }


}