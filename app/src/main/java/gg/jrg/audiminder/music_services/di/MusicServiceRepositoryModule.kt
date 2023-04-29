package gg.jrg.audiminder.music_services.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepository
import gg.jrg.audiminder.music_services.data.repositories.MusicServiceRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MusicServiceRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMusicServiceRepository(
        musicServiceRepositoryImpl: MusicServiceRepositoryImpl
    ): MusicServiceRepository

}
