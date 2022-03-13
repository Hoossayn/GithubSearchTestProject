package com.example.remote.di

import com.example.data.contracts.remote.GithubRemote
import com.example.remote.impl.GithubRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GithubRemoteModule {

    @Binds
    abstract fun providesGithubRemote(impl: GithubRemoteImpl): GithubRemote
}