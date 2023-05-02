package com.gthio.todosandboxkmm.domain.repository

import arrow.core.Either
import com.gthio.todosandboxkmm.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing todo items.
 */
interface TodoRepository {
    /**
     * Observe the list of todo items.
     * @return A flow of the list of todo items.
     */
    fun observeTodoList(): Flow<List<TodoItem>>

    /**
     * Add a todo item to the list.
     * @param todoItem The todo item to add.
     * @return Either a DuplicateTodo error or Unit.
     */
    suspend fun addTodoItem(todoItem: TodoItem): Either<DuplicateTodo, Unit>

    /**
     * Remove a todo item from the list.
     * @param todoItem The todo item to remove.
     * @return Either a TodoNotFound error or Unit.
     */
    suspend fun removeTodoItem(todoItem: TodoItem): Either<TodoNotFound, Unit>
}

/**
 * Error for when a todo item is not found.
 */
object TodoNotFound

/**
 * Error for when a todo item is a duplicate.
 */
data class DuplicateTodo(val item: TodoItem)