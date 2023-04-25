package gg.jrg.audiminder.authentication.di

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
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.authentication.data.services.AuthorizationService
import gg.jrg.audiminder.authentication.data.services.SpotifyAuthorizationService
import gg.jrg.audiminder.authentication.domain.usecase.AuthorizationUseCases
import gg.jrg.audiminder.core.util.ActivityStateFlowWrapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthorizationUseCases(
        authorizationRepository: AuthorizationRepository
    ): AuthorizationUseCases {
        return AuthorizationUseCases(authorizationRepository)
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
    @AuthorizationServiceMap
    @IntoMap
    @StringKey("SPOTIFY")
    @Singleton
    fun provideSpotifyAuthorizationService(
        activityStateFlowWrapper: ActivityStateFlowWrapper,
        spotifyDefaultCredentialStore: SpotifyDefaultCredentialStore
    ): SpotifyAuthorizationService {
        return SpotifyAuthorizationService(activityStateFlowWrapper, spotifyDefaultCredentialStore)
    }

    @Provides
    @AuthorizationServiceMap
    fun provideAuthorizationServices(
        spotifyAuthorizationService: SpotifyAuthorizationService
    ): Map<String, AuthorizationService> {
        return mapOf(
            AuthServiceType.SPOTIFY.serviceAsString to spotifyAuthorizationService
        )
    }

}
