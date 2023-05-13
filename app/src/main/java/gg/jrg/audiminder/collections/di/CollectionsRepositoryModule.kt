package gg.jrg.audiminder.collections.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.data.repositories.CollectionsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CollectionsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCollectionsRepository(
        collectionsRepositoryImpl: CollectionsRepositoryImpl
    ): CollectionsRepository

}