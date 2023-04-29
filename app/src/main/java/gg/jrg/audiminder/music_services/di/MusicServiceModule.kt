package gg.jrg.audiminder.music_services.di

import android.content.Context
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import gg.jrg.audiminder.BuildConfig
import gg.jrg.audiminder.core.util.ActivityStateFlowWrapper
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository
import gg.jrg.audiminder.music_services.data.services.MusicServiceProvider
import gg.jrg.audiminder.music_services.data.services.SpotifyMusicServiceProvider
import gg.jrg.audiminder.music_services.domain.usecase.MusicServiceUseCases
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
    fun provideSpotifyCredentialStore(
        @ApplicationContext context: Context
    ): SpotifyDefaultCredentialStore {
        return SpotifyDefaultCredentialStore(
            clientId = BuildConfig.SPOTIFY_CLIENT_ID,
            redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI,
            applicationContext = context
        )
    }

    @Provides
    @MusicServiceProviderMap
    @IntoMap
    @StringKey("SPOTIFY")
    @Singleton
    fun provideSpotifyAuthorizationService(
        activityStateFlowWrapper: ActivityStateFlowWrapper,
        spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore
    ): SpotifyMusicServiceProvider {
        return SpotifyMusicServiceProvider(activityStateFlowWrapper, spotifyDefaultCredentialStore)
    }

    @Provides
    @MusicServiceProviderMap
    fun provideMusicServiceProviders(
        spotifyMusicServiceProvider: SpotifyMusicServiceProvider
    ): Map<String, MusicServiceProvider> {
        return mapOf(
            MusicServiceType.SPOTIFY.serviceAsString to spotifyMusicServiceProvider
        )
    }

}
