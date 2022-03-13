package com.example.domain.usecases

import com.example.domain.executor.PostExecutorThread
import com.example.domain.model.GithubUser
import com.example.domain.repositories.GithubUsersRepository
import com.example.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteUsersUseCase @Inject constructor(
    private val repository: GithubUsersRepository,
    private val postExecution: PostExecutorThread
) : FlowUseCase<Unit, List<GithubUser>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecution.io

    override fun execute(params: Unit?): Flow<List<GithubUser>> {
        return repository.getFavoriteUsers()
    }
}