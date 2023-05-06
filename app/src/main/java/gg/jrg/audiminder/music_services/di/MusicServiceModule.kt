package gg.jrg.audiminder.music_services.di

import android.content.Context
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import gg.jrg.audiminder.BuildConfig
import gg.jrg.audiminder.core.util.ActivityStateFlowWrapper
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.providers.MusicServiceProvider
import gg.jrg.audiminder.music_services.data.providers.SpotifyMusicServiceProvider
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository
import gg.jrg.audiminder.music_services.domain.usecase.MusicServiceUseCases
import gg.jrg.audiminder.music_services.util.clearSharedPreferences
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicServiceModule {

    @Provides
    fun provideMusicServicesUseCases(
        musicServiceRepository: MusicServiceRepository
    ): MusicServiceUseCases {
        return MusicServiceUseCases(musicServiceRepository)
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
//    @MusicServiceProviderMap
    @IntoMap
    @MusicServiceTypeKey(MusicServiceType.SPOTIFY)
    @Singleton
    fun provideSpotifyAuthorizationService(
        activityStateFlowWrapper: ActivityStateFlowWrapper,
        spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore
    ): SpotifyMusicServiceProvider {
        return SpotifyMusicServiceProvider(activityStateFlowWrapper, spotifyDefaultCredentialStore)
    }

    @Provides
    @MusicServiceProviderMap
    @Singleton
    fun provideMusicServiceProviders(
        spotifyMusicServiceProvider: SpotifyMusicServiceProvider
    ): Map<MusicServiceType, MusicServiceProvider> {
        return mapOf(
            MusicServiceType.SPOTIFY to spotifyMusicServiceProvider
        )
    }

}
