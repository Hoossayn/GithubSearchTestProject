package com.example.github_ui.mappers

import com.example.domain.model.GithubUser
import com.example.github_ui.models.GithubUsersModel
import javax.inject.Inject

class GithubUsersModelMapper @Inject constructor(
) : ModelMapper<GithubUsersModel, GithubUser> {

    override fun mapToModel(domain: GithubUser): GithubUsersModel {
        return domain.run {
            GithubUsersModel(
                domain.id,
                domain.login,
                domain.avatarUrl,
                domain.githubUrl,
                domain.isFavorite
            )
        }
    }

    override fun mapToDomain(model: GithubUsersModel): GithubUser {
        return model.run {
            GithubUser(
                model.id,
                model.login,
                model.avatarUrl,
                model.githubUrl,
                model.isFavorite
            )
        }
    }
}