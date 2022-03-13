package com.example.domain.usecases

import com.example.domain.fakes.DummyData
import com.example.domain.repositories.GithubUsersRepository
import com.example.searchgithubusers.fakes.TestPostExecutionThread
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetFavoriteUsersUseCaseTest {

    private lateinit var sut: GetFavoriteUsersUseCase

    @Mock
    lateinit var repository: GithubUsersRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = GetFavoriteUsersUseCase(repository, TestPostExecutionThread())
    }


    @Test
    fun `get favorite users`() = runBlockingTest {
        val user = DummyData.makeGithubUser()
        val list = DummyData.makeGithubUserList(1)
        repository.favoriteUser(user)
        whenever(
            repository.getFavoriteUsers()
        ).thenReturn(
            flow {
                emit(
                    list
                )
            }
        )
        sut.execute()
        verify(repository).getFavoriteUsers()

    }


}