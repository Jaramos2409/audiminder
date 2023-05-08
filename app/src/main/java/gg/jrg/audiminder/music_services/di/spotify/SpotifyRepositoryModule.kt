package gg.jrg.audiminder.music_services.di.spotify

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepository
import gg.jrg.audiminder.music_services.data.repositories.SpotifyRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpotifyRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSpotifyRepository(
        spotifyRepositoryImpl: SpotifyRepositoryImpl
    ): SpotifyRepository

}