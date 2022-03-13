package com.example.cache.data

import com.example.cache.models.GithubUsersCacheModel
import com.example.data.models.GithubUserEntity

object DummyData {


    fun makeGithubEntityList(count: Int): List<GithubUserEntity> {
        val models = mutableListOf<GithubUserEntity>()
        repeat(count) {
            models.add(makeGithubEntityModel())
        }
        return models
    }

    fun makeGithubEntityModel(): GithubUserEntity {
        return GithubUserEntity(1, "name", "picture", "url")
    }

    fun makeGithubUserCacheModel(): GithubUsersCacheModel {
        return GithubUsersCacheModel(1, "name", "picture", "url", false)
    }
}