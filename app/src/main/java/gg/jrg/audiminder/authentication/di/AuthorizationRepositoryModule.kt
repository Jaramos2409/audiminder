package gg.jrg.audiminder.authentication.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepository
import gg.jrg.audiminder.authentication.data.repositories.AuthorizationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthorizationRepositoryModule {

    @Binds
    abstract fun bindAuthorizationRepository(
        authorizationRepositoryImpl: AuthorizationRepositoryImpl
    ): AuthorizationRepository

}