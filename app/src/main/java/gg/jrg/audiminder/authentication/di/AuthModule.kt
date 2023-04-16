package gg.jrg.audiminder.authentication.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.SecureSpotifyAuthorizationTokenStorage
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.authentication.data.services.AuthorizationService
import gg.jrg.audiminder.authentication.data.services.SpotifyAuthorizationService
import gg.jrg.audiminder.authentication.domain.usecase.AuthorizationUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideMapOfServices(
        spotifyAuthorizationService: SpotifyAuthorizationService
    ): HashMap<AuthServiceType, AuthorizationService> {
        return hashMapOf(
            AuthServiceType.SPOTIFY to spotifyAuthorizationService
        )
    }

    @Provides
    fun provideAuthorizationUseCases(
        authorizationRepository: AuthorizationRepository
    ): AuthorizationUseCases {
        return AuthorizationUseCases(authorizationRepository)
    }

    @Provides
    fun provideSecureSpotifyAuthorizationTokenStorage(@ApplicationContext context: Context): SecureSpotifyAuthorizationTokenStorage {
        return SecureSpotifyAuthorizationTokenStorage(context)
    }

    @Provides
    @Singleton
    fun provideSpotifyAuthorizationService(
        @ApplicationContext context: Context,
        secureSpotifyAuthorizationTokenStorage: SecureSpotifyAuthorizationTokenStorage
    ): SpotifyAuthorizationService {
        return SpotifyAuthorizationService(context, secureSpotifyAuthorizationTokenStorage)
    }

}