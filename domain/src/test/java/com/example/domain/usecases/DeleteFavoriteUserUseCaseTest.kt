package com.example.domain.usecases

import com.example.domain.fakes.DummyData
import com.example.domain.model.GithubUser
import com.example.searchgithubusers.fakes.FakeGithubRepository
import com.example.searchgithubusers.fakes.TestPostExecutionThread
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class DeleteFavoriteUserUseCaseTest {

    private val repository = FakeGithubRepository()
    private val deleteUser = DeleteFavoriteUseCase(repository, TestPostExecutionThread())

    @ExperimentalCoroutinesApi
    @Test
    fun `check that delete user`() = runBlockingTest {
        val user = DummyData.makeGithubUser()
        repository.favoriteUser(user)
        deleteUser(user)
        val result: List<GithubUser> = repository.getFavoriteUsers().first()
        assertThat(result).isEmpty()
    }
}