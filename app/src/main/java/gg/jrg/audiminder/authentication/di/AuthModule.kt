package gg.jrg.audiminder.authentication.di

import android.content.Context
import android.content.SharedPreferences
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
import javax.inject.Named
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
    @Named("secure_spotify_authorization_token_storage_shared_preferences")
    fun provideSecureSpotifyAuthorizationTokenStorageSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "secure_spotify_authorization_token_storage",
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideSecureSpotifyAuthorizationTokenStorage(
        @Named("secure_spotify_authorization_token_storage_shared_preferences") sharedPreferences: SharedPreferences
    ): SecureSpotifyAuthorizationTokenStorage {
        return SecureSpotifyAuthorizationTokenStorage(sharedPreferences)
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