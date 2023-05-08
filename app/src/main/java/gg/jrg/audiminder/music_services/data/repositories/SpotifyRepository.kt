package gg.jrg.audiminder.music_services.data.repositories

import gg.jrg.audiminder.music_services.data.source.local.SpotifyLocalDataSource
import gg.jrg.audiminder.music_services.data.source.remote.SpotifyApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface SpotifyRepository {
    val displayName: StateFlow<String>
    suspend fun refreshDisplayName()
    suspend fun clearDisplayName()
}

class SpotifyRepositoryImpl @Inject constructor(
    private val spotifyApiService: SpotifyApiService,
    private val spotifyLocalDataSource: SpotifyLocalDataSource
) : SpotifyRepository {

    private val _displayName = MutableStateFlow("")
    override val displayName: StateFlow<String>
        get() = _displayName

    override suspend fun refreshDisplayName() {
        val localDisplayName = spotifyLocalDataSource.getDisplayName()

        if (localDisplayName.isSuccess) {
            _displayName.value = localDisplayName.getOrNull()!!
        }

        val remoteDisplayName = spotifyApiService.getUserData()?.displayName

        if (remoteDisplayName != null) {
            spotifyLocalDataSource.setDisplayName(remoteDisplayName)
            _displayName.value = remoteDisplayName
        } else {
            throw Exception("There was an issue fetching the display name from Spotify API.")
        }
    }

    override suspend fun clearDisplayName() {
        spotifyLocalDataSource.clearDisplayName()
        _displayName.value = ""
    }

}
