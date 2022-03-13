package com.example.data

import com.example.data.models.GithubUserEntity
import com.example.domain.model.GithubUser

object DummyData {

    fun makeGithubUserEntity(): GithubUserEntity {
        return GithubUserEntity(1, "name", "picture", "url", false)
    }

    fun makeGithubUserList(count: Int): List<GithubUserEntity> {
        val list = mutableListOf<GithubUserEntity>()
        repeat(count) {
            list.add(makeGithubUserEntity())
        }
        return list
    }

    fun makeGithubUser(): GithubUser {
        return GithubUser(1, "name", "picture", "url", false)
    }
}