package com.example.remote.impl

import com.example.data.contracts.remote.GithubRemote
import com.example.data.models.GithubUserResponseEntity
import com.example.remote.ApiService
import com.example.remote.mappers.GithubResponseNetworkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRemoteImpl @Inject constructor(
    private val mapper: GithubResponseNetworkMapper,
    private val apiService: ApiService
) : GithubRemote {
    override fun searchUsers(query: String, pageNumber: Int): Flow<GithubUserResponseEntity> {
        return flow {
            emit(mapper.mapFromModel(apiService.searchUsers(page = pageNumber, query = query)))
        }
    }
}