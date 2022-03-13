package com.example.domain.model

data class GithubUserResponse(
    val totalCount: Int,
    val items: List<GithubUser>
)

