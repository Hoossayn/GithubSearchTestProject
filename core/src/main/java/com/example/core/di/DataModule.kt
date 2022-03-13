package com.example.core.di

import com.example.data.impl.GithubUsersRepositoryImpl
import com.example.domain.repositories.GithubUsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun providesGithubRepository(githubRepositoryImpl: GithubUsersRepositoryImpl): GithubUsersRepository
}