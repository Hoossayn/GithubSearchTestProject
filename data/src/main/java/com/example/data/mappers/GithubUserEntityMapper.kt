package com.example.data.mappers

import com.example.data.mappers.base.EntityMapper
import com.example.data.models.GithubUserEntity
import com.example.domain.model.GithubUser
import javax.inject.Inject

class GithubUserEntityMapper @Inject constructor(
) : EntityMapper<GithubUserEntity, GithubUser> {
    override fun mapFromEntity(entity: GithubUserEntity): GithubUser {
        return entity.run {
            GithubUser(
                id,
                login, avatarUrl, githubUrl, isFavorite
            )
        }
    }

    override fun mapToEntity(domain: GithubUser): GithubUserEntity {
        return domain.run {
            GithubUserEntity(
                id,
                login, avatarUrl, githubUrl, isFavorite
            )
        }
    }
}