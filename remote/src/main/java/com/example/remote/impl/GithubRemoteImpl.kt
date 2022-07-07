package com.example.remote.impl

import com.example.data.contracts.remote.GithubRemote
import com.example.data.models.GithubRepoResponseEntity
import com.example.data.models.GithubUserResponseEntity
import com.example.remote.ApiService
import com.example.remote.mappers.GithubRepoResponseMapper
import com.example.remote.mappers.GithubResponseNetworkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRemoteImpl @Inject constructor(
    private val mapper: GithubResponseNetworkMapper,
    private val repoMapper: GithubRepoResponseMapper,
    private val apiService: ApiService
) : GithubRemote {
    override fun searchUsers(query: String, pageNumber: Int): Flow<GithubUserResponseEntity> {
        return flow {
            emit(mapper.mapFromModel(apiService.searchUsers(page = pageNumber, query = query)))
        }
    }

    override fun searchRepositories(): Flow<List<GithubRepoResponseEntity>> {
        return flow {
            emit(repoMapper.mapFromEntityList(apiService.searchRepositories()))
        }
    }
}