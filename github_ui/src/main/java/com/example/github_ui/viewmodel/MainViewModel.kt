package com.example.github_ui.viewmodel

import androidx.lifecycle.*
import com.example.domain.usecases.*
import com.example.github_ui.mappers.GithubUsersModelMapper
import com.example.github_ui.models.GithubUsersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchUsers: SearchUsersUseCase,
    private val loadMore: LoadMoreUsersUseCase,
    private val favoriteUserUseCase: FavoriteUserUseCase,
    private val deleteFavoritesUseCase: DeleteFavoriteUseCase,
    private val mapper: GithubUsersModelMapper,
    private val checkFavoritesUseCase: CheckFavoriteStatusUseCase,
    private val getUsers: GetFavoriteUsersUseCase
) : ViewModel() {

    private val _users = MutableLiveData<LatestUiState<List<GithubUsersModel>>>()
    val users: LiveData<LatestUiState<List<GithubUsersModel>>> = _users

    private val usersList = mutableListOf<GithubUsersModel>()

    private var params: SearchUsersUseCase.Params? = null

    private var currentPage: Int = 1

    private val _isLoadingMore = MutableLiveData<Boolean>()
    val isLoadingMore: LiveData<Boolean> = _isLoadingMore

    private val _favoriteUsers = MutableLiveData<LatestUiState<List<GithubUsersModel>>>()
    val favoriteUsers: LiveData<LatestUiState<List<GithubUsersModel>>> = _favoriteUsers

    private var _user = MutableLiveData<GithubUsersModel>()
    var user: LiveData<GithubUsersModel> = _user

    val isFavorite: LiveData<Boolean> = Transformations.switchMap(_user) {
        checkFavoritesUseCase(it.id).asLiveData()
    }

    private var count = 0

    private var lastQuery: String? = null


    fun setUserDetail(user: GithubUsersModel) {
        _user.value = user
    }

    private fun shouldFetch() = usersList.size < count

    fun setQueryInfo(query: String) {
        if (query == lastQuery) return

        query.also {
            lastQuery = it
        }
        searchGithubUsers()
    }

    fun searchGithubUsers() {
        if (lastQuery.isNullOrEmpty()) return

        _users.value = LatestUiState.Loading
        currentPage = 1
        params = SearchUsersUseCase.Params(
            query = lastQuery ?: "",
            pageNumber = currentPage
        )
        viewModelScope.launch {
            searchUsers(params)
                .catch {
                    _users.value = LatestUiState.Error("Cannot fetch users")
                }
                .map {
                    count = it.totalCount
                    mapper.mapToModelList(it.items)
                }
                .collect {
                    usersList.clear()
                    usersList.addAll(it)
                    _users.value = LatestUiState.Success(usersList)
                }
        }
    }

    fun fetchMoreUsers() {
        if (shouldFetch()) {
            _isLoadingMore.value = true
        }
        params = SearchUsersUseCase.Params(
            query = lastQuery ?: "",
            pageNumber = currentPage + 1
        )
        viewModelScope.launch {
            loadMore(params)
                .catch {
                    _users.value = LatestUiState.Error("Cannot fetch more users")
                }
                .map {
                    mapper.mapToModelList(it.items)
                }
                .collect {
                    _isLoadingMore.value = false
                    currentPage += 1
                    usersList.addAll(it)
                    _users.value = LatestUiState.Success(usersList)
                }
        }
    }

    fun getFavoriteUsers() {
        viewModelScope.launch {
            getUsers()
                .map {
                    mapper.mapToModelList(it)
                }.collect {
                    _favoriteUsers.value = LatestUiState.Success(it)
                }
        }
    }


    fun favoriteUser(user: GithubUsersModel) {
        viewModelScope.launch {
            if (!user.isFavorite) {
                favoriteUserUseCase(mapper.mapToDomain(user))
            } else {
                deleteFavoritesUseCase(mapper.mapToDomain(user))
            }
            toggleSelectedItem(user)
        }
    }


    fun favoriteUserDetail(user: GithubUsersModel) {
        viewModelScope.launch {
            if (isFavorite.value == true) {
                deleteFavoritesUseCase(mapper.mapToDomain(user))
            } else {
                favoriteUserUseCase(mapper.mapToDomain(user))
            }
            _user.value = user
            toggleSelectedItem(user)
        }
    }

    private fun toggleSelectedItem(item: GithubUsersModel) {
        usersList.find {
            it.id == item.id
        }?.isFavorite = item.isFavorite.not()
        _users.value = LatestUiState.Success(usersList)
    }
}

sealed class LatestUiState<out T : Any> {
    data class Success<out T : Any>(val users: T) : LatestUiState<T>()
    object Loading : LatestUiState<Nothing>()
    data class Error(val exception: String) : LatestUiState<Nothing>()
}