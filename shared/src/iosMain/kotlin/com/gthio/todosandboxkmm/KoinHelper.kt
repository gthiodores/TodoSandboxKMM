package com.gthio.todosandboxkmm

import com.gthio.todosandboxkmm.di.repositoryModule
import com.gthio.todosandboxkmm.domain.repository.TodoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(listOf(firebaseModule, repositoryModule))
    }
}

class TodoRepositoryHelper : KoinComponent {
    private val repository: TodoRepository by inject()
    fun getTodoRepository(): TodoRepository = repository
}