package com.example.data.mappers

import com.example.data.mappers.base.EntityMapper
import com.example.data.models.GithubRepoResponseEntity
import com.example.data.models.GithubUserEntity
import com.example.domain.model.GithubRepoResponse
import com.example.domain.model.GithubUser
import javax.inject.Inject

class GithubRepoEntityMapper @Inject constructor(
) : EntityMapper<GithubRepoResponseEntity, GithubRepoResponse> {
    override fun mapFromEntity(entity: GithubRepoResponseEntity): GithubRepoResponse {
        return entity.run {
            GithubRepoResponse(
                id,
                fullName,
                description
            )
        }
    }

    override fun mapToEntity(domain: GithubRepoResponse): GithubRepoResponseEntity {
        return domain.run {
            GithubRepoResponseEntity(
                id,
                fullName,
                description
            )
        }
    }
}