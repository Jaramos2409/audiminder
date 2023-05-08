package gg.jrg.audiminder.music_services.data.source.local

import gg.jrg.audiminder.core.data.dto.UserDetailsDTO
import gg.jrg.audiminder.core.data.source.UserDetailsDao
import gg.jrg.audiminder.core.data.source.UserDetailsKey
import javax.inject.Inject

interface SpotifyLocalDataSource {
    suspend fun getDisplayName(): Result<String?>
    suspend fun setDisplayName(displayName: String)
    suspend fun clearDisplayName()
}

class SpotifyLocalDataSourceImpl @Inject constructor(
    private val userDetailsDao: UserDetailsDao
) : SpotifyLocalDataSource {

    override suspend fun getDisplayName(): Result<String?> {
        return runCatching {
            val displayName = userDetailsDao.getUserDetail(UserDetailsKey.DISPLAY_NAME)
            if (displayName.isNullOrEmpty()) {
                throw IllegalArgumentException("Display name is empty or null")
            } else {
                displayName
            }
        }
    }

    override suspend fun setDisplayName(displayName: String) {
        userDetailsDao.insertUserDetail(
            UserDetailsDTO(
                key = UserDetailsKey.DISPLAY_NAME,
                value = displayName
            )
        )
    }

    override suspend fun clearDisplayName() {
        userDetailsDao.deleteUserDetail(UserDetailsKey.DISPLAY_NAME)
    }


}