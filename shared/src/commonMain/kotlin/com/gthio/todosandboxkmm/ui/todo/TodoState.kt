package com.gthio.todosandboxkmm.ui.todo

import com.gthio.todosandboxkmm.domain.model.ColorCode
import com.gthio.todosandboxkmm.domain.model.TodoItem

/**
 * Represents the state of the UI.
 *
 * @property items The list of todo items.
 * @property title The title of the todo item.
 * @property body The body of the todo item.
 * @property colorCode The color code of the todo item.
 * @property canAddTodo Whether the user can add a new todo item.
 *
 * @see TodoItem
 * @see ColorCode
 */
data class TodoState(
    val items: List<TodoItem> = emptyList(),
    val title: String = "",
    val body: String = "",
    val colorCode: ColorCode = ColorCode.WHITE,
    val exhausted: Boolean = false,
) {
    val canAddTodo: Boolean
        get() = title.isNotBlank() && body.isNotBlank()
}