package com.example.domain.usecases

import com.example.domain.executor.PostExecutorThread
import com.example.domain.model.GithubUser
import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteUserUseCase @Inject constructor(
    private val repository: GithubUsersRepository,
    private val postExecution: PostExecutorThread
) {

    suspend operator fun invoke(user: GithubUser) {
        withContext(postExecution.io) {
            repository.favoriteUser(user)
        }
    }
}