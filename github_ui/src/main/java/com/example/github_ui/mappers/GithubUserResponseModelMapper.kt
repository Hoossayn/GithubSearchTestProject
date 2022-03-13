package com.example.github_ui.mappers

import com.example.domain.model.GithubUserResponse
import com.example.github_ui.models.GithubUsersResponseModel
import javax.inject.Inject

class GithubUserResponseModelMapper @Inject constructor(
    private val mapper: GithubUsersModelMapper
) : ModelMapper<GithubUsersResponseModel, GithubUserResponse> {

    override fun mapToModel(domain: GithubUserResponse): GithubUsersResponseModel {
        return domain.run {
            GithubUsersResponseModel(
                domain.totalCount,
                mapper.mapToModelList(domain.items)
            )
        }
    }

    override fun mapToDomain(model: GithubUsersResponseModel): GithubUserResponse {
        return model.run {
            GithubUserResponse(
                model.totalCount,
                mapper.mapToDomainList(model.items)
            )
        }
    }
}