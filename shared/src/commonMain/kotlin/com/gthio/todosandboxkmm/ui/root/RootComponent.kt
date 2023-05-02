package com.gthio.todosandboxkmm.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.gthio.todosandboxkmm.ui.todo.TodoComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun navigateToAbout()

    fun navigateBackFromAbout()

    sealed class Child {
        class Todo(val component: TodoComponent) : Child()
        object About : Child()
    }
}