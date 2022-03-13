package com.example.data.contracts.remote

import com.example.data.models.GithubUserResponseEntity
import kotlinx.coroutines.flow.Flow

interface GithubRemote {
    fun searchUsers(query: String, pageNumber: Int): Flow<GithubUserResponseEntity>
}