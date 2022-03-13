package com.example.domain.usecases

import com.example.domain.executor.PostExecutorThread
import com.example.domain.repositories.GithubUsersRepository
import com.example.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckFavoriteStatusUseCase @Inject constructor(
    private val repository: GithubUsersRepository,
    private val postExecution: PostExecutorThread
) : FlowUseCase<Int, Boolean>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecution.io

    override fun execute(params: Int?): Flow<Boolean> {
        requireNotNull(params) { "params cannot be null" }
        return repository.checkFavoriteUser(params)
    }

}