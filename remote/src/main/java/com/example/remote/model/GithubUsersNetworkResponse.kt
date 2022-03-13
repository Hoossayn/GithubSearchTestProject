package com.example.remote.model

data class GithubUsersNetworkResponse(
    val total_count: Int,
    val items: List<GithubUsersNetworkModel>
)