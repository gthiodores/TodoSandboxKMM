package com.gthio.todosandboxkmm.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class DispatcherProvider {
    actual fun main(): CoroutineDispatcher = Dispatchers.Main

    actual fun io(): CoroutineDispatcher = Dispatchers.IO

    actual fun default(): CoroutineDispatcher = Dispatchers.Default

    actual fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}