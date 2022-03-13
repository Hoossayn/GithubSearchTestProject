package com.example.data.mappers

import com.example.data.DummyData.makeGithubUser
import com.example.data.DummyData.makeGithubUserEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GithubUserEntityMapperTest {

    private val sut = GithubUserEntityMapper()

    @Test
    fun `maps from entity to data model`() {
        val expected = makeGithubUserEntity()

        val result = sut.mapFromEntity(expected)

        assertThat(result.avatarUrl).isEqualTo(expected.avatarUrl)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.login).isEqualTo(expected.login)
        assertThat(result.githubUrl).isEqualTo(expected.githubUrl)
    }

    @Test
    fun `maps domain to entity`() {
        val expected = makeGithubUser()

        val result = sut.mapToEntity(expected)

        assertThat(result.avatarUrl).isEqualTo(expected.avatarUrl)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.login).isEqualTo(expected.login)
        assertThat(result.githubUrl).isEqualTo(expected.githubUrl)
    }
}