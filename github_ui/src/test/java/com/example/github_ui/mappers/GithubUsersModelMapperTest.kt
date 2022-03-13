package com.example.github_ui.mappers

import com.example.github_ui.mappers.DummyData.makeGithubUser
import com.example.github_ui.mappers.DummyData.makeGithubUserModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GithubUsersModelMapperTest {

    private val sut = GithubUsersModelMapper()

    @Test
    fun `maps to model`() {
        val input = makeGithubUser()

        val result = sut.mapToModel(input)

        assertThat(input.avatarUrl).isEqualTo(result.avatarUrl)
        assertThat(input.login).isEqualTo(result.login)
        assertThat(input.id).isEqualTo(result.id)
        assertThat(input.githubUrl).isEqualTo(result.githubUrl)
    }

    @Test
    fun `maps to domain`() {
        val input = makeGithubUserModel()

        val result = sut.mapToDomain(input)

        assertThat(input.avatarUrl).isEqualTo(result.avatarUrl)
        assertThat(input.login).isEqualTo(result.login)
        assertThat(input.id).isEqualTo(result.id)
        assertThat(input.githubUrl).isEqualTo(result.githubUrl)

    }
}