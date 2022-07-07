package com.example.remote

import com.example.remote.model.GithubRepoResponse
import com.example.remote.model.GithubUsersNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("per_page") per : Int = 10,
        @Query("page") page: Int = 1,
        @Query("q") query : String
    ) : GithubUsersNetworkResponse

    @GET("repositories")
    suspend fun searchRepositories(): List<GithubRepoResponse>
}