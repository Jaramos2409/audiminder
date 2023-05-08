package gg.jrg.audiminder.music_services.di.spotify

import android.content.Context
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.BuildConfig
import gg.jrg.audiminder.core.data.source.UserDetailsDao
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository
import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository
import gg.jrg.audiminder.music_services.data.source.local.SpotifyLocalDataSource
import gg.jrg.audiminder.music_services.data.source.local.SpotifyLocalDataSourceImpl
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyUserDetailsUseCases
import gg.jrg.audiminder.music_services.util.clearSharedPreferences
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpotifyModule {

    @Provides
    fun provideSpotifyAuthorizationUseCases(
        spotifyAuthorizationRepository: SpotifyAuthorizationRepository,
        spotifyRepository: SpotifyRepository
    ): SpotifyAuthorizationUseCases {
        return SpotifyAuthorizationUseCases(spotifyAuthorizationRepository, spotifyRepository)
    }

    @Provides
    fun provideSpotifyUserDetailsUseCases(
        spotifyRepository: SpotifyRepository
    ): SpotifyUserDetailsUseCases {
        return SpotifyUserDetailsUseCases(spotifyRepository)
    }

    @Provides
    @Singleton
    fun provideSpotifyCredentialStore(
        @ApplicationContext context: Context
    ): SpotifyDefaultCredentialStore {
        return try {
            SpotifyDefaultCredentialStore(
                clientId = BuildConfig.SPOTIFY_CLIENT_ID,
                redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI,
                applicationContext = context
            )
        } catch (e: Exception) {
            Timber.e(e)
            context.clearSharedPreferences()
            SpotifyDefaultCredentialStore(
                clientId = BuildConfig.SPOTIFY_CLIENT_ID,
                redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI,
                applicationContext = context
            )
        }
    }

    @Provides
    fun provideSpotifyLocalDataSource(
        userDetailsDao: UserDetailsDao
    ): SpotifyLocalDataSource {
        return SpotifyLocalDataSourceImpl(userDetailsDao)
    }

}
