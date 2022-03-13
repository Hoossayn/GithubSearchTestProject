package com.example.searchgithubusers.fakes

import com.example.domain.executor.PostExecutorThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestPostExecutionThread : PostExecutorThread {

    override val main: CoroutineDispatcher
        get() = TestCoroutineDispatcher()

    override val io: CoroutineDispatcher
        get() = TestCoroutineDispatcher()

    override val default: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
}