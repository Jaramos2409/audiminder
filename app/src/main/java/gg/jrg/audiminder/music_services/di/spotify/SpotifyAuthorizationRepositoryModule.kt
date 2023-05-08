package gg.jrg.audiminder.music_services.di.spotify

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepository
import gg.jrg.audiminder.music_services.data.repositories.SpotifyAuthorizationRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpotifyAuthorizationRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSpotifyAuthorizationRepository(
        spotifyAuthorizationRepositoryImpl: SpotifyAuthorizationRepositoryImpl
    ): SpotifyAuthorizationRepository

}
