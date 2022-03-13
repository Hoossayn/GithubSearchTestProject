package com.example.data.impl

import com.example.data.contracts.cache.GithubCache
import com.example.data.contracts.remote.GithubRemote
import com.example.data.mappers.GithubUserEntityMapper
import com.example.data.models.GithubUserEntity
import com.example.domain.model.GithubUser
import com.example.domain.model.GithubUserResponse
import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubUsersRepositoryImpl @Inject constructor(
    private val mapper: GithubUserEntityMapper,
    private val usersRemote: GithubRemote,
    private val usersCache: GithubCache
) : GithubUsersRepository {
    override fun searchUsers(query: String, pageNumber: Int): Flow<GithubUserResponse> {
        return flow {
            emitAll(usersRemote.searchUsers(query, pageNumber).map {
                it.items.map { entity: GithubUserEntity ->
                    val isFavoriteUser: Boolean = usersCache.checkIfUserExist(entity.id)
                    entity.apply { isFavorite = isFavoriteUser }
                    mapper.mapFromEntity(entity)
                }
                GithubUserResponse(it.total_count, mapper.mapFromEntityList(it.items))
            })
        }
    }

    override suspend fun favoriteUser(user: GithubUser) {
        user.apply { isFavorite = isFavorite.not() }
        usersCache.saveUser(mapper.mapToEntity(user))
    }

    override suspend fun deleteFavoriteUser(user: GithubUser) {
        usersCache.removeUser(mapper.mapToEntity(user))
    }

    override fun getFavoriteUsers(): Flow<List<GithubUser>> {
        return flow {
            emitAll(usersCache.getUsers().map {
                mapper.mapFromEntityList(it)
            })
        }
    }

    override fun checkFavoriteUser(id: Int): Flow<Boolean> {
        return flow {
            emit(usersCache.checkIfUserExist(id))
        }
    }
}