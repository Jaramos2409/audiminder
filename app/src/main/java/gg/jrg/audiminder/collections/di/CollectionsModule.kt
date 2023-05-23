package gg.jrg.audiminder.collections.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.collections.data.repositories.CollectionsRepository
import gg.jrg.audiminder.collections.data.source.AlbumCollectionCrossRefDao
import gg.jrg.audiminder.collections.data.source.AlbumCollectionDao
import gg.jrg.audiminder.collections.data.source.AlbumDao
import gg.jrg.audiminder.collections.data.source.TrackDao
import gg.jrg.audiminder.collections.data.source.local.CollectionsLocalDataSource
import gg.jrg.audiminder.collections.data.source.local.CollectionsLocalDataSourceImpl
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.core.data.source.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CollectionsModule {

    @Provides
    fun provideCollectionsUseCases(
        collectionsRepository: CollectionsRepository
    ): CollectionsUseCases {
        return CollectionsUseCases(
            collectionsRepository
        )
    }

    @Provides
    fun provideCollectionsLocalDataSource(
        albumCollectionDao: AlbumCollectionDao,
        albumCollectionCrossRefDao: AlbumCollectionCrossRefDao,
        albumDao: AlbumDao,
        trackDao: TrackDao,
        appDatabase: AppDatabase,
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): CollectionsLocalDataSource {
        return CollectionsLocalDataSourceImpl(
            albumCollectionDao,
            albumCollectionCrossRefDao,
            albumDao,
            trackDao,
            appDatabase,
            ioDispatcher
        )
    }

}