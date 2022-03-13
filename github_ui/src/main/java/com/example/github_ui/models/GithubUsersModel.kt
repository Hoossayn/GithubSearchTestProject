package com.example.github_ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUsersModel(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val githubUrl : String,
    var isFavorite: Boolean
) : Parcelable