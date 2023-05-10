package gg.jrg.audiminder.music_services.data.repositories

import com.adamratzman.spotify.utils.Market
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.core.util.ImageService
import gg.jrg.audiminder.core.util.logChanges
import gg.jrg.audiminder.core.util.throwIfFailure
import gg.jrg.audiminder.music_services.data.source.local.SpotifyLocalDataSource
import gg.jrg.audiminder.music_services.data.source.remote.SpotifyApiService
import gg.jrg.audiminder.music_services.util.mapToAlbum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

interface SpotifyRepository {
    val displayName: StateFlow<String>
    val profileImageFilePath: StateFlow<String>
    val userMarket: StateFlow<Market>
    suspend fun refreshUserData()
    suspend fun clearDisplayName()
    suspend fun clearProfileImageFilePath()
    suspend fun searchSpotifyAndFetchAlbums(query: String): Flow<List<Album>>
}

class SpotifyRepositoryImpl @Inject constructor(
    private val spotifyApiService: SpotifyApiService,
    private val spotifyLocalDataSource: SpotifyLocalDataSource,
    private val imageService: ImageService
) : SpotifyRepository {

    private val _displayName =
        MutableStateFlow("").apply { logChanges("SpotifyRepositoryImpl _displayName") }
    override val displayName: StateFlow<String>
        get() = _displayName

    private val _profileImageFilePath =
        MutableStateFlow("").apply { logChanges("SpotifyRepositoryImpl _profileImageFilePath") }
    override val profileImageFilePath: StateFlow<String>
        get() = _profileImageFilePath

    private val _userMarket =
        MutableStateFlow(Market.US).apply { logChanges("SpotifyRepositoryImpl _userMarket") }
    override val userMarket: StateFlow<Market>
        get() = _userMarket

    override suspend fun refreshUserData() {
        val localDisplayName = spotifyLocalDataSource.getDisplayName()
        if (localDisplayName.isSuccess) {
            _displayName.value = localDisplayName.getOrNull()!!
        }

        val localProfileImageFilePath = spotifyLocalDataSource.getProfileImageFilePath()
        if (localProfileImageFilePath.isSuccess) {
            _profileImageFilePath.value = localProfileImageFilePath.getOrNull()!!
        }

        val localUserMarket = spotifyLocalDataSource.getUserMarket()
        if (localUserMarket.isSuccess) {
            _userMarket.value = localUserMarket.getOrNull()!!
        }

        if (localDisplayName.isSuccess
            && localProfileImageFilePath.isSuccess
            && localUserMarket.isSuccess
        ) {
            return
        }

        val userData = spotifyApiService.getUserData()

        if (localDisplayName.isFailure) {
            val remoteDisplayName = userData?.displayName
            if (remoteDisplayName != null) {
                spotifyLocalDataSource.setDisplayName(remoteDisplayName).throwIfFailure()
                _displayName.value = remoteDisplayName
            } else {
                throw Exception("There was an issue fetching the display name from Spotify API.")
            }
        }

        if (localProfileImageFilePath.isFailure) {
            userData?.images?.forEach { image ->
                val bitmap = imageService.downloadImage(image.url)
                if (bitmap != null) {
                    val fileName = UUID.randomUUID().toString()
                    val filePath = imageService.saveImageToFile(bitmap, fileName)
                    spotifyLocalDataSource.setProfileImageFilePath(filePath).throwIfFailure()
                    _profileImageFilePath.value = filePath
                }
            }
        }

        if (localUserMarket.isFailure) {
            val remoteUserMarket = userData?.country?.let { Market.valueOf(it) }
            if (remoteUserMarket != null) {
                spotifyLocalDataSource.setUserMarket(remoteUserMarket).throwIfFailure()
                _userMarket.value = remoteUserMarket
            } else {
                throw Exception("There was an issue fetching the user market from Spotify API.")
            }
        }
    }

    override suspend fun clearDisplayName() {
        spotifyLocalDataSource.clearDisplayName().throwIfFailure()
        _displayName.value = ""
    }

    override suspend fun clearProfileImageFilePath() {
        imageService.deleteImageFile(_profileImageFilePath.value).let { wasDeleted ->
            if (!wasDeleted) {
                Timber.w("Image file was not deleted. File path: ${_profileImageFilePath.value}")
            }
        }
        spotifyLocalDataSource.clearProfileImageFilePath().throwIfFailure()
        _profileImageFilePath.value = ""
    }

    override suspend fun searchSpotifyAndFetchAlbums(query: String): Flow<List<Album>> {
        val searchResults =
            spotifyApiService.searchForAlbumsAndReturnResult(query, userMarket.value)

        val listOfAlbumsFromSearchResults = searchResults?.albums?.items?.map { album ->
            album.mapToAlbum()
        } ?: emptyList()

        Timber.i("listOfAlbumsFromSearchResults: $listOfAlbumsFromSearchResults")

        return flow { emit(listOfAlbumsFromSearchResults) }
    }

}
