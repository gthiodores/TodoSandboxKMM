package com.gthio.todosandboxkmm.ui.todo

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.gthio.todosandboxkmm.domain.model.ColorCode
import com.gthio.todosandboxkmm.domain.repository.TodoRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

/**
 * This is a wrapper around the TodoStore that allows us to use it in a Compose UI.
 * It is a ComponentContext, which is a Compose concept that allows us to store state
 * and pass it down to child components.
 *
 * @param componentContext The ComponentContext that this component is a part of.
 * @param factory The StoreFactory that will be used to create the TodoStore.
 * @param repository The TodoRepository that will be used to save the Todo.
 *
 * @see com.gthio.todosandboxkmm.ui.TodoStoreFactory
 * @see com.gthio.todosandboxkmm.ui.TodoStore
 * @see com.gthio.todosandboxkmm.domain.repository.TodoRepository
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TodoComponent(
    componentContext: ComponentContext,
    factory: StoreFactory,
    repository: TodoRepository,
) : ComponentContext by componentContext {
    private val store = instanceKeeper.getStore { TodoStoreFactory(factory, repository).create() }
//    private val pager = instanceKeeper.getOrCreate("Pager") {
//        Pager(
//            clientScope = CoroutineScope(SupervisorJob() + Dispatchers.Main),
//            config = ,
//            initialKey = ,
//            getItems = {  },
//        )
//    }

    /**
     * This is the state of the TodoStore, which is a StateFlow.
     */
    @NativeCoroutinesState
    val uiState: StateFlow<TodoState>
        get() = store.stateFlow

    init {
        loadNext()
    }

    /**
     * This is a function that will be called when the body of the Todo field is updated.
     */
    fun onBodyUpdated(value: String) {
        store.accept(TodoIntent.UpdateBodyField(value))
    }

    /**
     * This is a function that will be called when the title of the Todo field is updated.
     */
    fun onTitleUpdated(value: String) {
        store.accept(TodoIntent.UpdateTitleField(value))
    }

    /**
     * This is a function that will be called when the Todo is added.
     */
    fun onAddTodo() {
        store.accept(TodoIntent.AddTodo)
    }

    fun loadNext() {
        store.accept(TodoIntent.LoadTodo)
    }

    /**
     * This is a function that will be called when the color of the Todo is updated.
     */
    fun onColorUpdated(value: ColorCode) {
        store.accept(TodoIntent.UpdateColor(value))
    }
}