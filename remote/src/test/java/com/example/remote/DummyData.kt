package com.example.remote

import com.example.data.models.GithubUserEntity
import com.example.data.models.GithubUserResponseEntity
import com.example.remote.model.GithubUsersNetworkModel
import com.example.remote.model.GithubUsersNetworkResponse

object DummyData {

    fun makeGithubEntityModel(): GithubUserEntity {
        return GithubUserEntity(1, "name", "picture", "url")
    }

    fun makeGithubUserNetworkEntityList(count : Int) : List<GithubUserEntity> {
        val list = mutableListOf<GithubUserEntity>()
        repeat(count) {
            list.add(makeGithubEntityModel())
        }
        return list
    }

    fun makeGithubUserNetworkModel(): GithubUsersNetworkModel {
        return GithubUsersNetworkModel(1, "name", "picture", "url")
    }

    fun makeGithubUserNetworkModelList(count : Int) : List<GithubUsersNetworkModel> {
        val list = mutableListOf<GithubUsersNetworkModel>()
        repeat(count) {
            list.add(makeGithubUserNetworkModel())
        }
        return list
    }

    fun makeGithubNetworkModel() : GithubUsersNetworkResponse {
        return GithubUsersNetworkResponse(1, makeGithubUserNetworkModelList(1))
    }

    fun makeGithubUserResponseEntity() : GithubUserResponseEntity {
        return GithubUserResponseEntity(1, makeGithubUserNetworkEntityList(1))
    }
}