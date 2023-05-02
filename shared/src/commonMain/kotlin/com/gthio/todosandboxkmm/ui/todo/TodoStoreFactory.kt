package com.gthio.todosandboxkmm.ui.todo

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.gthio.todosandboxkmm.domain.model.ColorCode
import com.gthio.todosandboxkmm.domain.model.TodoItem
import com.gthio.todosandboxkmm.domain.repository.TodoRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

// A factory for creating TodoStore instances.
internal class TodoStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: TodoRepository,
) {
    fun create(): TodoStore {
        return object : TodoStore, Store<TodoIntent, TodoState, Nothing> by storeFactory.create(
            name = "Todo Store",
            bootstrapper = SimpleBootstrapper(Unit),
            initialState = TodoState(),
            executorFactory = ::TodoExecutor,
            reducer = TodoReducer,
        ) {}
    }

    /**
     * A [Message] that can be dispatched to the [TodoStore].
     */
    private sealed interface Message {
        data class TodoListUpdated(val items: List<TodoItem>) : Message
        data class BodyUpdated(val value: String) : Message
        data class TitleUpdated(val value: String) : Message
        data class ColorUpdated(val colorCode: ColorCode) : Message
    }

    /**
     * A [CoroutineExecutor] that handles [TodoIntent]s and Actions.
     */
    private inner class TodoExecutor : CoroutineExecutor<TodoIntent, Unit, TodoState, Message, Nothing>() {
        override fun executeIntent(intent: TodoIntent, getState: () -> TodoState) {
            when (intent) {
                TodoIntent.AddTodo -> addTodo(getState())
                is TodoIntent.UpdateBodyField -> dispatch(Message.BodyUpdated(intent.value))
                is TodoIntent.UpdateTitleField -> dispatch(Message.TitleUpdated(intent.value))
                is TodoIntent.UpdateColor -> dispatch(Message.ColorUpdated(intent.value))
            }
        }

        override fun executeAction(action: Unit, getState: () -> TodoState) {
            repository
                .observeTodoList()
                .onEach { items -> dispatch(Message.TodoListUpdated(items)) }
                .launchIn(scope)
        }

        /**
         * Adds a new [TodoItem] to the repository.
         */
        private fun addTodo(state: TodoState) {
            val todo = TodoItem(title = state.title, content = state.body, colorCode = state.colorCode)
            scope.launch { repository.addTodoItem(todoItem = todo) }
        }
    }

    /**
     * A [Reducer] that handles [Message]s and updates the [TodoState].
     */
    private object TodoReducer : Reducer<TodoState, Message> {
        override fun TodoState.reduce(msg: Message): TodoState {
            return when (msg) {
                is Message.TodoListUpdated -> copy(items = msg.items)
                is Message.BodyUpdated -> copy(body = msg.value)
                is Message.TitleUpdated -> copy(title = msg.value)
                is Message.ColorUpdated -> copy(colorCode = msg.colorCode)
            }
        }
    }
}