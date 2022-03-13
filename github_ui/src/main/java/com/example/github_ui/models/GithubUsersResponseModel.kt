package com.example.github_ui.models

data class GithubUsersResponseModel(
    val totalCount: Int,
    val items: List<GithubUsersModel>
)