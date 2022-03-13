package com.example.github_ui.mappers

import com.example.domain.model.GithubUser
import com.example.domain.model.GithubUserResponse
import com.example.github_ui.models.GithubUsersModel
import com.example.github_ui.models.GithubUsersResponseModel

object DummyData {

    fun makeGithubUser(): GithubUser {
        return GithubUser(1, "pawnjester", "image", "url", false)
    }

    fun makeGithubUserModel(): GithubUsersModel {
        return GithubUsersModel(1, "pawnjester", "image", "url", false)
    }

    fun makeGithubUserResponse(): GithubUserResponse {
        return GithubUserResponse(1, makeGithubUserList(1))
    }

    private fun makeGithubUsersModelList(count: Int): List<GithubUsersModel> {
        val list = mutableListOf<GithubUsersModel>()
        repeat(count) {
            list.add(makeGithubUserModel())
        }
        return list
    }

    fun makeGithubUserList(count: Int): List<GithubUser> {
        val list = mutableListOf<GithubUser>()
        repeat(count) {
            list.add(makeGithubUser())
        }
        return list
    }

    fun makeGithubUserResponseModel(): GithubUsersResponseModel {
        return GithubUsersResponseModel(1, makeGithubUsersModelList(1))
    }
}