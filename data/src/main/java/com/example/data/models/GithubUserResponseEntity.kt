package com.example.data.models

data class GithubUserResponseEntity(
    val total_count: Int,
    val items: List<GithubUserEntity>
)

