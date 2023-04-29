package gg.jrg.audiminder.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.collections.data.source.AlbumCollectionCrossRefDao
import gg.jrg.audiminder.collections.data.source.AlbumCollectionDao
import gg.jrg.audiminder.collections.data.source.AlbumDao
import gg.jrg.audiminder.collections.data.source.TrackDao
import gg.jrg.audiminder.core.data.source.AppDatabase
import gg.jrg.audiminder.core.util.ActivityStateFlowWrapper
import gg.jrg.audiminder.music_services.data.source.SupportedMusicServiceDao
import gg.jrg.audiminder.reminder.data.source.ReminderDao
import gg.jrg.audiminder.search.data.source.SearchHistoryDao
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val PREFS_NAME = "app_prefs"

    @Provides
    @Named("app_shared_preferences")
    fun provideAppSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).createFromAsset("database/audiminderdb.db").build()
    }

    @Provides
    fun provideAlbumCollectionDao(appDatabase: AppDatabase): AlbumCollectionDao {
        return appDatabase.albumCollectionDao()
    }

    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase): AlbumDao {
        return appDatabase.albumDao()
    }

    @Provides
    fun provideTrackDao(appDatabase: AppDatabase): TrackDao {
        return appDatabase.trackDao()
    }

    @Provides
    fun provideReminderDao(appDatabase: AppDatabase): ReminderDao {
        return appDatabase.reminderDao()
    }

    @Provides
    fun provideAlbumCollectionCrossRefDao(appDatabase: AppDatabase): AlbumCollectionCrossRefDao {
        return appDatabase.albumCollectionCrossRefDao()
    }

    @Provides
    fun provideSearchHistoryDao(appDatabase: AppDatabase): SearchHistoryDao {
        return appDatabase.searchHistoryDao()
    }

    @Provides
    fun provideSupportedAuthorizationServiceDao(appDatabase: AppDatabase): SupportedMusicServiceDao {
        return appDatabase.supportedAuthorizationServiceDao()
    }

    @Provides
    @Singleton
    fun provideActivityStateFlow(): ActivityStateFlowWrapper {
        return ActivityStateFlowWrapper(MutableStateFlow(null))
    }

}
