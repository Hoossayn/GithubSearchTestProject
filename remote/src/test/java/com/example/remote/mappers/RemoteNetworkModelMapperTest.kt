package com.example.remote.mappers

import com.example.remote.DummyData.makeGithubEntityModel
import com.example.remote.DummyData.makeGithubUserNetworkModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RemoteNetworkModelMapperTest {

    private val sut = RemoteNetworkModelMapper()

    @Test
    fun `maps model to entity`() {

        val expected = makeGithubUserNetworkModel()

        val result = sut.mapFromModel(expected)

        assertThat(result.avatarUrl).isEqualTo(expected.avatar_url)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.login).isEqualTo(expected.login)
        assertThat(result.type).isEqualTo(expected.type)
        assertThat(result.githubUrl).isEqualTo(expected.html_url)
    }


    @Test
    fun `maps domain to network model`() {

        val expected = makeGithubEntityModel()

        val result = sut.mapToModel(expected)
        assertThat(result.avatar_url).isEqualTo(expected.avatarUrl)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.login).isEqualTo(expected.login)
        assertThat(result.type).isEqualTo(expected.type)
        assertThat(result.html_url).isEqualTo(expected.githubUrl)

    }
}