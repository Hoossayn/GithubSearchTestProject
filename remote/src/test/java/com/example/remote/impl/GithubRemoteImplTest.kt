package com.example.remote.impl

import com.example.data.contracts.remote.GithubRemote
import com.example.data.models.GithubUserResponseEntity
import com.example.remote.mappers.GithubResponseNetworkMapper
import com.example.remote.mappers.RemoteNetworkModelMapper
import com.example.remote.utils.PAGE
import com.example.remote.utils.RequestDispatcher
import com.example.remote.utils.SEARCH_QUERY
import com.example.remote.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class GithubRemoteImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var remote: GithubRemote

    private val githubRemoteModelMapper = GithubResponseNetworkMapper(RemoteNetworkModelMapper())

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = RequestDispatcher()
        mockWebServer.start()
        remote = GithubRemoteImpl(githubRemoteModelMapper, makeTestApiService(mockWebServer))
    }

    @Test
    fun `check whether searchUsers returns a list`() = runBlocking {
        val users: Flow<GithubUserResponseEntity> = remote.searchUsers(SEARCH_QUERY, PAGE)
        assertThat(users.first().items).isNotEmpty()
        assertThat(users.first().items.size).isEqualTo(1)
    }

    @Test
    fun `check that search users returns the correct data`() = runBlocking {
        val users: Flow<GithubUserResponseEntity> = remote.searchUsers(SEARCH_QUERY, PAGE)
        assertThat(users.first().items.first().login).isEqualTo("pawnjester")
        assertThat(users.first().items.first().avatarUrl).isEqualTo("https://avatars.githubusercontent.com/u/26750279?v=4")
        assertThat(users.first().items.first().githubUrl).isEqualTo("https://github.com/pawnjester")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}