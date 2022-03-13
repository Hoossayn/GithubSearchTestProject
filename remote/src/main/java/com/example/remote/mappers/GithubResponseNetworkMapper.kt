package com.example.remote.mappers

import com.example.data.models.GithubUserResponseEntity
import com.example.remote.mappers.base.RemoteModelMapper
import com.example.remote.model.GithubUsersNetworkResponse
import javax.inject.Inject

class GithubResponseNetworkMapper @Inject constructor(
    private val mapper: RemoteNetworkModelMapper
) : RemoteModelMapper<GithubUsersNetworkResponse, GithubUserResponseEntity> {
    override fun mapFromModel(model: GithubUsersNetworkResponse): GithubUserResponseEntity {
        return model.run {
            GithubUserResponseEntity(
                model.total_count,
                mapper.mapFromEntityList(model.items)
            )
        }
    }

    override fun mapToModel(domain: GithubUserResponseEntity): GithubUsersNetworkResponse {
        return domain.run {
            GithubUsersNetworkResponse(
                domain.total_count,
                mapper.mapFromDomainList(domain.items)
            )
        }
    }
}