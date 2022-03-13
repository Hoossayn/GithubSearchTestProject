package com.example.data.repository

import com.example.data.DummyData.makeGithubUser
import com.example.data.fakes.FakeGithubCache
import com.example.data.fakes.FakeGithubRemote
import com.example.data.impl.GithubUsersRepositoryImpl
import com.example.data.mappers.GithubUserEntityMapper
import com.example.domain.model.GithubUser
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GithubUserRepositoryImplTest {

    private val mapper = GithubUserEntityMapper()
    private val userRemote = FakeGithubRemote()
    private val userCache = FakeGithubCache()

    private val repository = GithubUsersRepositoryImpl(
        mapper, userRemote, userCache
    )

    @Test
    fun `check that get favorites returns a list of favorite users`(): Unit = runBlocking {
        val user = makeGithubUser()
        repository.favoriteUser(user)

        val result: List<GithubUser> = repository.getFavoriteUsers().first()
        assertThat(result).containsExactly(user)
    }

    @Test
    fun `check delete favorite`() = runBlocking {
        val user = makeGithubUser()
        repository.favoriteUser(user)
        repository.deleteFavoriteUser(user)

        val result: List<GithubUser> = repository.getFavoriteUsers().first()

        assertThat(result).isEmpty()
    }

    @Test
    fun `check that search users`() = runBlocking {
        val result = repository.searchUsers("hoossayn", 1).first()

        assertThat(result.totalCount).isEqualTo(1)
        assertThat(result.items).isNotEmpty()
    }
}