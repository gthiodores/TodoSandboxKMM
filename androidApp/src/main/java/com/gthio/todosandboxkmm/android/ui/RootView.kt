package com.gthio.todosandboxkmm.android.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.gthio.todosandboxkmm.ui.root.RootComponent

@Composable
fun RootView(component: RootComponent) {
    Children(stack = component.childStack) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Todo -> TodoRoute(
                onAbout = component::navigateToAbout,
                todoComponent = instance.component,
            )

            is RootComponent.Child.About -> AboutRoute(
                onBack = component::navigateBackFromAbout
            )
        }
    }
}