package gg.jrg.audiminder.music_services.data.repositories

import gg.jrg.audiminder.core.util.ImageService
import gg.jrg.audiminder.core.util.logChanges
import gg.jrg.audiminder.core.util.throwIfFailure
import gg.jrg.audiminder.music_services.data.source.local.SpotifyLocalDataSource
import gg.jrg.audiminder.music_services.data.source.remote.SpotifyApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

interface SpotifyRepository {
    val displayName: StateFlow<String>
    val profileImageFilePath: StateFlow<String>
    suspend fun refreshUserData()
    suspend fun clearDisplayName()
    suspend fun clearProfileImageFilePath()
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

    override suspend fun refreshUserData() {
        val localDisplayName = spotifyLocalDataSource.getDisplayName()
        if (localDisplayName.isSuccess) {
            _displayName.value = localDisplayName.getOrNull()!!
        }

        val localProfileImageFilePath = spotifyLocalDataSource.getProfileImageFilePath()
        if (localProfileImageFilePath.isSuccess) {
            _profileImageFilePath.value = localProfileImageFilePath.getOrNull()!!
        }

        if (localDisplayName.isSuccess && localProfileImageFilePath.isSuccess) {
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
    }

    override suspend fun clearDisplayName() {
        spotifyLocalDataSource.clearDisplayName().throwIfFailure()
        _displayName.value = ""
    }

    override suspend fun clearProfileImageFilePath() {
        Timber.d("clearProfileImageFilePath: Start")

        imageService.deleteImageFile(_profileImageFilePath.value).let { wasDeleted ->
            if (!wasDeleted) {
                Timber.w("Image file was not deleted. File path: ${_profileImageFilePath.value}")
            }
        }
        Timber.d("clearProfileImageFilePath: Image deleted")

        spotifyLocalDataSource.clearProfileImageFilePath().throwIfFailure()
        Timber.d("clearProfileImageFilePath: Local data source cleared")

        _profileImageFilePath.value = ""
        Timber.d("clearProfileImageFilePath: _profileImageFilePath set to empty string")
    }

}
