package gg.jrg.audiminder.core.di

import android.content.SharedPreferences
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.dto.SupportedAuthorizationServiceDTO
import gg.jrg.audiminder.authentication.data.source.SupportedAuthorizationServiceDao
import kotlinx.coroutines.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InitializerModule {

    private const val KEY_FIRST_LAUNCH = "first_launch"

    @Singleton
    @Provides
    fun provideDatabaseInitializer(
        @Named("app_shared_preferences") sharedPreferences: SharedPreferences,
        supportedAuthorizationServiceDao: SupportedAuthorizationServiceDao
    ): Callback {
        return object : Callback() {
            private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                val isFirstLaunch = sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
                if (isFirstLaunch) {
                    coroutineScope.launch {
                        supportedAuthorizationServiceDao.insertAll(
                            SupportedAuthorizationServiceDTO(
                                0,
                                AuthServiceType.SPOTIFY.serviceAsString
                            ),
                        )
                    }
                    sharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
                }
            }
        }
    }
}
