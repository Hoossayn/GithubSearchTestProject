package com.example.cache.di

import android.content.Context
import com.example.cache.room.GithubUserDao
import com.example.cache.room.GithubUsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {

    @[Provides Singleton]
    fun providesDatabase(@ApplicationContext context: Context): GithubUsersDatabase {
        return GithubUsersDatabase.getInstance(context)
    }

    @[Provides Singleton]
    fun providesUserDao(database: GithubUsersDatabase): GithubUserDao {
        return database.userDao()
    }
}