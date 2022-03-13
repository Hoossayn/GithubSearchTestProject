package com.example.cache.mappers

import com.example.cache.mappers.base.CacheModelMapper
import com.example.cache.models.GithubUsersCacheModel
import com.example.data.models.GithubUserEntity
import javax.inject.Inject

class GithubUsersCacheModelMapper @Inject constructor() : CacheModelMapper<GithubUsersCacheModel, GithubUserEntity> {

    override fun mapToModel(entity: GithubUserEntity): GithubUsersCacheModel {
        return entity.run {
            GithubUsersCacheModel(
                entity.id,
                entity.login,
                entity.avatarUrl,
                entity.githubUrl,
                entity.isFavorite
            )
        }
    }

    override fun mapToEntity(model: GithubUsersCacheModel): GithubUserEntity {
        return model.run {
            GithubUserEntity(
                model.id,
                model.login,
                model.avatarUrl,
                model.githubUrl,
                model.isFavorite
            )
        }
    }
}