package com.gthio.todosandboxkmm.ui.todo

import com.gthio.todosandboxkmm.domain.model.ColorCode

/**
 * Represents a user's intent to interact with the UI.
 */
sealed class TodoIntent {
    // Represents a user's intent to add a new todo.
    object AddTodo : TodoIntent()

    // Represents a user's intent to fill the title text field
    data class UpdateTitleField(val value: String) : TodoIntent()

    // Represents a user's intent to fill the body text field
    data class UpdateBodyField(val value: String) : TodoIntent()

    // Represents a user's intent to change the color of the todo
    data class UpdateColor(val value: ColorCode) : TodoIntent()
    object LoadTodo : TodoIntent()
}