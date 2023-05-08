package gg.jrg.audiminder.music_services.di.spotify

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.music_services.data.source.remote.SpotifyApiService
import gg.jrg.audiminder.music_services.data.source.remote.SpotifyApiServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpotifyApiServiceModule {

    @Binds
    @Singleton
    abstract fun bindSpotifyApiService(
        spotifyApiServiceImpl: SpotifyApiServiceImpl
    ): SpotifyApiService

}