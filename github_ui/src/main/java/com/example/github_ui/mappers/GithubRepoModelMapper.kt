package com.example.github_ui.mappers

import com.example.domain.model.GithubRepoResponse
import com.example.github_ui.models.GithubRepoModel
import javax.inject.Inject

class GithubRepoModelMapper @Inject constructor(
) : ModelMapper<GithubRepoModel, GithubRepoResponse> {

    override fun mapToModel(domain: GithubRepoResponse): GithubRepoModel {
        return domain.run {
            GithubRepoModel(
                domain.id,
                domain.fullName,
                domain.description
            )
        }
    }

    override fun mapToDomain(model: GithubRepoModel): GithubRepoResponse {
        return model.run {
            GithubRepoResponse(
                model.id,
                model.fullName,
                model.description
            )
        }
    }
}