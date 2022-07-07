package com.example.domain.repositories

import com.example.domain.model.GithubRepoResponse
import com.example.domain.model.GithubUser
import com.example.domain.model.GithubUserResponse
import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {

    fun searchUsers(query: String, pageNumber: Int = 0): Flow<GithubUserResponse>

    fun searchRepositories(): Flow<List<GithubRepoResponse>>

    suspend fun favoriteUser(user: GithubUser)

    suspend fun deleteFavoriteUser(user: GithubUser)

    fun getFavoriteUsers(): Flow<List<GithubUser>>

    fun checkFavoriteUser(id: Int) : Flow<Boolean>
}