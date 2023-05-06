package com.gthio.todosandboxkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.gthio.todosandboxkmm.android.ui.RootView
import com.gthio.todosandboxkmm.ui.root.DefaultRootComponent
import com.gthio.todosandboxkmm.ui.root.RootComponent
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    private val module = module {
        single<ComponentContext> { defaultComponentContext() }
        single<RootComponent> { DefaultRootComponent(get(), get()) }
    }

    init {
        loadKoinModules(module)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent: RootComponent by inject()

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RootView(component = rootComponent)
                }
            }
        }
    }
}
