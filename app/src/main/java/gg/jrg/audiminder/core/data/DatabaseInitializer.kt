package gg.jrg.audiminder.core.data

import android.content.SharedPreferences
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Lazy
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.dto.SupportedAuthorizationServiceDTO
import gg.jrg.audiminder.authentication.data.source.SupportedAuthorizationServiceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


private const val KEY_FIRST_LAUNCH = "first_launch"

class DatabaseInitializer @Inject constructor(
    @Named("app_shared_preferences") private val sharedPreferences: SharedPreferences,
    private val supportedAuthorizationServiceDao: Lazy<SupportedAuthorizationServiceDao>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            initializeDatabase()
        }
    }

    private fun initializeDatabase() {
        val isFirstLaunch = sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
        if (isFirstLaunch) {
            supportedAuthorizationServiceDao.get().insertAll(
                SupportedAuthorizationServiceDTO(
                    serviceName = AuthServiceType.SPOTIFY.serviceAsString
                ),
            )

            sharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
        }
    }
}
