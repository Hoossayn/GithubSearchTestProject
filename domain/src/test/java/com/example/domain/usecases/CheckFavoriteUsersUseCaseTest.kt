package com.example.domain.usecases

import com.example.domain.fakes.DummyData
import com.example.domain.model.GithubUser
import com.example.searchgithubusers.fakes.FakeGithubRepository
import com.example.searchgithubusers.fakes.TestPostExecutionThread
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CheckFavoriteUsersUseCaseTest {

    private val repository = FakeGithubRepository()
    private val checkUser = CheckFavoriteStatusUseCase(repository, TestPostExecutionThread())

    @ExperimentalCoroutinesApi
    @Test
    fun `check that user is a favorite`() = runBlockingTest {
        val user = DummyData.makeGithubUser()
        repository.favoriteUser(user)
        checkUser(user.id)
        val result: List<GithubUser> = repository.getFavoriteUsers().first()
        Truth.assertThat(result).isNotEmpty()
    }
}