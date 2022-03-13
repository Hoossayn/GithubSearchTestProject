package com.example.data.models

data class GithubUserEntity(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val githubUrl: String,
    var isFavorite: Boolean = false

)