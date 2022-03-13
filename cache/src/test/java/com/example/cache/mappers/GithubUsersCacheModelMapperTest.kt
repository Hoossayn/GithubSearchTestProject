package com.example.cache.mappers

import com.example.cache.data.DummyData.makeGithubEntityModel
import com.example.cache.data.DummyData.makeGithubUserCacheModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GithubUsersCacheModelMapperTest {

    private val sut = GithubUsersCacheModelMapper()

    @Test
    fun `maps entity to model`() {
        val expected = makeGithubEntityModel()

        val result = sut.mapToModel(expected)

        assertThat(result.avatarUrl).isEqualTo(expected.avatarUrl)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.login).isEqualTo(expected.login)
        assertThat(result.githubUrl).isEqualTo(expected.githubUrl)
    }


    @Test
    fun `maps model to entity`() {
        val expected = makeGithubUserCacheModel()

        val result = sut.mapToEntity(expected)

        assertThat(result.avatarUrl).isEqualTo(expected.avatarUrl)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.login).isEqualTo(expected.login)
        assertThat(result.githubUrl).isEqualTo(expected.githubUrl)
    }
}