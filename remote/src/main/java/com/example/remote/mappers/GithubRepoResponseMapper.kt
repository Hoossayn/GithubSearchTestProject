package com.example.remote.mappers

import com.example.data.models.GithubRepoResponseEntity
import com.example.remote.mappers.base.RemoteModelMapper
import com.example.remote.model.GithubRepoResponse
import javax.inject.Inject

class GithubRepoResponseMapper @Inject constructor(
    private val mapper: RemoteNetworkModelMapper
):RemoteModelMapper<GithubRepoResponse, GithubRepoResponseEntity> {

    override fun mapFromModel(model: GithubRepoResponse): GithubRepoResponseEntity{
        return model.run {
            GithubRepoResponseEntity(
                model.id,
                model.full_name,
                model.description
            )
        }
    }

    override fun mapToModel(domain: GithubRepoResponseEntity): GithubRepoResponse {
        return domain.run {
            GithubRepoResponse(
                domain.id,
                domain.fullName,
                domain.description
            )
        }
    }


}