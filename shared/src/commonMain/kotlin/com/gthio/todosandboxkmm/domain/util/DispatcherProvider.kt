package com.gthio.todosandboxkmm.domain.util

import kotlinx.coroutines.CoroutineDispatcher

expect class DispatcherProvider() {
    fun main(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    fun default(): CoroutineDispatcher

    fun unconfined(): CoroutineDispatcher
}