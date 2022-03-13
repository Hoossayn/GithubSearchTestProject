package com.example.cache.impl

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cache.data.DummyData.makeGithubEntityModel
import com.example.cache.mappers.GithubUsersCacheModelMapper
import com.example.cache.room.GithubUsersDatabase
import com.example.data.contracts.cache.GithubCache
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GithubUsersCacheImplTest {

    private lateinit var database: GithubUsersDatabase
    private lateinit var cache: GithubCache

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GithubUsersDatabase::class.java
        ).allowMainThreadQueries().build()

        cache = GithubUsersCacheImpl(
            database.userDao(),
            GithubUsersCacheModelMapper()
        )
    }

    @Test
    fun `check that getUsers returns a list of users`() = runBlocking {
        val model = makeGithubEntityModel()

        cache.saveUser(model)
        val result = cache.getUsers().first()
        assertThat(result[0]).isEqualTo(model)
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun `check that save user saves a user in the database`() = runBlocking {
        val entity = makeGithubEntityModel()
        cache.saveUser(entity)

        val result = cache.getUsers().first()
        assertThat(result).isNotEmpty()
    }
}