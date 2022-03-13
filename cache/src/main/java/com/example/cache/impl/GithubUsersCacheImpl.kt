package com.example.cache.impl

import com.example.cache.mappers.GithubUsersCacheModelMapper
import com.example.cache.room.GithubUserDao
import com.example.data.contracts.cache.GithubCache
import com.example.data.models.GithubUserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubUsersCacheImpl @Inject constructor(
    private val dao: GithubUserDao,
    private val mapper: GithubUsersCacheModelMapper
) : GithubCache {


    override suspend fun saveUser(user: GithubUserEntity) {
        dao.favoriteUser(mapper.mapToModel(user))
    }

    override suspend fun removeUser(user: GithubUserEntity) {
        dao.removeUserFromFavorite(mapper.mapToModel(user))
    }

    override fun getUsers(): Flow<List<GithubUserEntity>> {
        val models = dao.getUsersFromCache()

        return models.map {
            mapper.mapToEntityList(it)
        }
    }

    override suspend fun checkIfUserExist(id: Int): Boolean {
        return dao.checkIfUserIsFavorite(id)
    }


}