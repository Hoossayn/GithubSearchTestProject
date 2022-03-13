package com.example.github_ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.model.GithubUser
import com.example.domain.usecases.*
import com.example.github_ui.MainCoroutineRule
import com.example.github_ui.mappers.DummyData
import com.example.github_ui.mappers.DummyData.makeGithubUserModel
import com.example.github_ui.mappers.DummyData.makeGithubUserResponse
import com.example.github_ui.mappers.GithubUsersModelMapper
import com.example.github_ui.models.GithubUsersModel
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

class MainViewModelTest {

    private lateinit var sut: MainViewModel

    @Mock
    lateinit var loadMore: LoadMoreUsersUseCase

    @Mock
    lateinit var searchUsers: SearchUsersUseCase

    @Mock
    lateinit var favoriteUserUseCase: FavoriteUserUseCase

    @Mock
    lateinit var getUsers: GetFavoriteUsersUseCase

    @Mock
    lateinit var deleteFavoritesUseCase: DeleteFavoriteUseCase

    @Mock
    lateinit var mapper: GithubUsersModelMapper

    @Mock
    lateinit var checkFavoriteStatusUseCase: CheckFavoriteStatusUseCase

    @Mock
    lateinit var uiObserver: Observer<LatestUiState<List<GithubUsersModel>>>

    @Mock
    lateinit var detailObserver: Observer<GithubUsersModel>

    @Captor
    private lateinit var captorUI: ArgumentCaptor<LatestUiState<List<GithubUsersModel>>>

    @Captor
    private lateinit var captorUser: ArgumentCaptor<GithubUsersModel>

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        sut = MainViewModel(
            searchUsers,
            loadMore, favoriteUserUseCase,
            deleteFavoritesUseCase, mapper,
            checkFavoriteStatusUseCase, getUsers
        )
    }

    @Test
    fun `search users`() {
        sut.users.observeForever(uiObserver)

        val params = SearchUsersUseCase.Params("pawnjester", 1)
        Mockito.`when`(searchUsers(params)).thenReturn(
            flow {
                emit(makeGithubUserResponse())
            }
        )
        sut.setQueryInfo("pawnjester")
        Mockito.verify(uiObserver, times(2)).onChanged(captorUI.capture())
    }

    @Test
    fun `test to confirm a user is favorited`() = runBlocking {

        val users = DummyData.makeGithubUser()
        val usersModel = makeGithubUserModel()

        Mockito.`when`(favoriteUserUseCase(users)).thenReturn(Unit)

        sut.favoriteUser(usersModel)
        assertThat(usersModel.isFavorite).isEqualTo(false)
    }

    @Test
    fun `set user details`() {
        sut.user.observeForever(detailObserver)

        val model = makeGithubUserModel()

        sut.setUserDetail(model)
        Mockito.verify(detailObserver, times(1)).onChanged(captorUser.capture())

        assertThat(captorUser.value).isEqualTo(model)
    }

    @Test
    fun `favorite user`() {
        sut.user.observeForever(detailObserver)

        val model = makeGithubUserModel()

        sut.favoriteUserDetail(model)
        Mockito.verify(detailObserver, times(1)).onChanged(captorUser.capture())

        assertThat(captorUser.value).isEqualTo(model)
    }

    @Test
    fun `test to confirm favoriting a user`() = runBlocking {
        sut.favoriteUsers.observeForever(uiObserver)

        val users = DummyData.makeGithubUser()
        val usersModel = makeGithubUserModel()

        Mockito.`when`(favoriteUserUseCase(users)).thenReturn(Unit)

        sut.favoriteUser(usersModel)
        assertThat(usersModel.isFavorite).isEqualTo(false)
    }

    @Test
    fun `test to get favorite users`() = runBlocking {
        sut.favoriteUsers.observeForever(uiObserver)

        val users: List<GithubUser> = DummyData.makeGithubUserList(1)

        Mockito.`when`(getUsers()).thenReturn(
            flow {
                emit(users)
            }
        )
        sut.getFavoriteUsers()
        Mockito.verify(uiObserver, times(1)).onChanged(captorUI.capture())
        sut.favoriteUsers.removeObserver(uiObserver)
    }
}