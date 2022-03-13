package com.example.cache.di

import com.example.cache.impl.GithubUsersCacheImpl
import com.example.data.contracts.cache.GithubCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GithubUsersCacheModule {

    @Binds
    abstract fun providesGithubUsersCache(githubUsersCacheImpl: GithubUsersCacheImpl): GithubCache
}