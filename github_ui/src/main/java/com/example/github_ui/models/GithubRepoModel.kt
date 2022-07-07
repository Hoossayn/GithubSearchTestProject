package com.example.github_ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepoModel(
    val id: Int,
    val fullName: String,
    val description: String,
) : Parcelable