package gg.jrg.audiminder.authentication.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthorizationRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthorizationRepository(
        authorizationRepositoryImpl: AuthorizationRepositoryImpl
    ): AuthorizationRepository

}
