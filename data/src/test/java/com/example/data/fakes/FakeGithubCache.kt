package com.example.data.fakes

import com.example.data.contracts.cache.GithubCache
import com.example.data.models.GithubUserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGithubCache : GithubCache {

    private val cache = LinkedHashMap<String, GithubUserEntity>()

    override suspend fun saveUser(user: GithubUserEntity) {
        cache[user.login] = user
    }

    override suspend fun removeUser(user: GithubUserEntity) {
        cache.clear()
    }

    override fun getUsers(): Flow<List<GithubUserEntity>> {
        return flowOf(cache.values.toList())
    }

    override suspend fun checkIfUserExist(id: Int): Boolean {
        return cache.containsKey(id)
    }


}