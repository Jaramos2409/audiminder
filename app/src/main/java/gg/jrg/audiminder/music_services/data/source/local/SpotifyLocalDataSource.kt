package gg.jrg.audiminder.music_services.data.source.local

import gg.jrg.audiminder.core.data.dto.UserDetailsDTO
import gg.jrg.audiminder.core.data.source.UserDetailsDao
import gg.jrg.audiminder.core.data.source.UserDetailsKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SpotifyLocalDataSource {
    suspend fun getDisplayName(): Result<String?>
    suspend fun setDisplayName(displayName: String): Result<Unit>
    suspend fun clearDisplayName(): Result<Unit>
    suspend fun setProfileImageFilePath(filePath: String): Result<Unit>
    suspend fun getProfileImageFilePath(): Result<String?>
    suspend fun clearProfileImageFilePath(): Result<Unit>
}

class SpotifyLocalDataSourceImpl @Inject constructor(
    private val userDetailsDao: UserDetailsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SpotifyLocalDataSource {

    override suspend fun getDisplayName(): Result<String?> = withContext(ioDispatcher) {
        return@withContext runCatching {
            val displayName = userDetailsDao.getUserDetail(UserDetailsKey.DISPLAY_NAME)
            if (displayName.isNullOrEmpty()) {
                throw IllegalArgumentException("Display name is empty or null")
            } else {
                displayName
            }
        }
    }

    override suspend fun setDisplayName(displayName: String): Result<Unit> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                userDetailsDao.insertUserDetail(
                    UserDetailsDTO(
                        key = UserDetailsKey.DISPLAY_NAME,
                        value = displayName
                    )
                )
            }
        }

    override suspend fun clearDisplayName(): Result<Unit> = withContext(ioDispatcher) {
        return@withContext runCatching {
            userDetailsDao.deleteUserDetail(UserDetailsKey.DISPLAY_NAME)
        }
    }

    override suspend fun setProfileImageFilePath(filePath: String): Result<Unit> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                userDetailsDao.insertUserDetail(
                    UserDetailsDTO(
                        key = UserDetailsKey.USER_PROFILE_IMAGE,
                        value = filePath
                    )
                )
            }
        }

    override suspend fun getProfileImageFilePath(): Result<String?> = withContext(ioDispatcher) {
        return@withContext runCatching {
            val profileImage = userDetailsDao.getUserDetail(UserDetailsKey.USER_PROFILE_IMAGE)
            if (profileImage.isNullOrEmpty()) {
                throw IllegalArgumentException("Profile image is empty or null")
            } else {
                profileImage
            }
        }
    }

    override suspend fun clearProfileImageFilePath(): Result<Unit> = withContext(ioDispatcher) {
        return@withContext runCatching {
            userDetailsDao.deleteUserDetail(UserDetailsKey.USER_PROFILE_IMAGE)
        }
    }

}