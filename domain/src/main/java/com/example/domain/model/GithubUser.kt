package com.example.domain.model

data class GithubUser(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val githubUrl : String,
    var isFavorite: Boolean
)