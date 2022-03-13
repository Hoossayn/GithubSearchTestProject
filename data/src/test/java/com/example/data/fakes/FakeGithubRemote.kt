package com.example.data.fakes

import com.example.data.DummyData.makeGithubUserList
import com.example.data.contracts.remote.GithubRemote
import com.example.data.models.GithubUserResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGithubRemote : GithubRemote {
    override fun searchUsers(query: String, pageNumber: Int): Flow<GithubUserResponseEntity> {
        return flowOf(GithubUserResponseEntity(1, makeGithubUserList(1)))
    }

}