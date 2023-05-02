package com.gthio.todosandboxkmm.di

import com.gthio.todosandboxkmm.data.repository.FirebaseTodoRepository
import com.gthio.todosandboxkmm.domain.repository.TodoRepository
import com.gthio.todosandboxkmm.domain.util.DispatcherProvider
import org.koin.dsl.module

val repositoryModule = module {
    factory { DispatcherProvider() }
    single<TodoRepository> {
        FirebaseTodoRepository(
            firestore = get(),
            dispatcher = get(),
        )
    }
}