package com.gthio.todosandboxkmm.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.gthio.todosandboxkmm.domain.repository.TodoRepository
import com.gthio.todosandboxkmm.ui.root.RootComponent.Child
import com.gthio.todosandboxkmm.ui.todo.TodoComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val todoRepository: TodoRepository,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Todo,
        childFactory = ::childFactory,
    )
    override val childStack: Value<ChildStack<*, Child>>
        get() = stack

    private fun childFactory(config: Configuration, context: ComponentContext): Child {
        return when (config) {
            Configuration.About -> Child.About
            Configuration.Todo -> Child.Todo(
                component = TodoComponent(
                    componentContext = context,
                    factory = DefaultStoreFactory(),
                    repository = todoRepository,
                ),
            )
        }
    }

    override fun navigateToAbout() {
        navigation.push(Configuration.About)
    }

    override fun navigateBackFromAbout() {
        navigation.pop()
    }

    @Parcelize
    private sealed class Configuration : Parcelable {
        object Todo : Configuration()
        object About : Configuration()
    }
}