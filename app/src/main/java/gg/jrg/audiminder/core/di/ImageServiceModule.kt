package gg.jrg.audiminder.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.core.util.CoilImageService
import gg.jrg.audiminder.core.util.ImageService

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageServiceModule {

    @Binds
    abstract fun bindImageService(coilImageService: CoilImageService): ImageService

}