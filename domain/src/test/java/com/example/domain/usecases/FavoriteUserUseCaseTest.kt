package com.example.domain.usecases

import com.example.domain.fakes.DummyData.makeGithubUser
import com.example.domain.model.GithubUser
import com.example.searchgithubusers.fakes.FakeGithubRepository
import com.example.searchgithubusers.fakes.TestPostExecutionThread
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class FavoriteUserUseCaseTest {

    private val repository = FakeGithubRepository()
    private val favoriteUser = FavoriteUserUseCase(repository, TestPostExecutionThread())

    @ExperimentalCoroutinesApi
    @Test
    fun `check that favorite user`() = runBlockingTest {
        val user = makeGithubUser()
        favoriteUser(user)
        val result: List<GithubUser> = repository.getFavoriteUsers().first()
        assertThat(user).isEqualTo(result[0])
    }
}