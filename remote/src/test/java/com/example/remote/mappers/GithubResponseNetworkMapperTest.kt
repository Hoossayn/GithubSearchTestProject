package com.example.remote.mappers

import com.example.data.models.GithubUserResponseEntity
import com.example.remote.DummyData.makeGithubNetworkModel
import com.example.remote.DummyData.makeGithubUserResponseEntity
import com.example.remote.model.GithubUsersNetworkResponse
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GithubResponseNetworkMapperTest {

    private val sut = GithubResponseNetworkMapper(RemoteNetworkModelMapper())

    @Test
    fun `maps to entity`() {
        val expected: GithubUsersNetworkResponse = makeGithubNetworkModel()
        val result: GithubUserResponseEntity = sut.mapFromModel(expected)

        assertThat(result.items).isEqualTo(result.items)
        assertThat(result.total_count).isEqualTo(result.total_count)
    }


    @Test
    fun `maps to model`() {

        val expected = makeGithubUserResponseEntity()
        val result = sut.mapToModel(expected)

        assertThat(result.items).isEqualTo(result.items)
        assertThat(result.total_count).isEqualTo(result.total_count)
    }
}