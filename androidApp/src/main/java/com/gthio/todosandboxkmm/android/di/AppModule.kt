package com.gthio.todosandboxkmm.android.di

import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.ViewModelStore
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.gthio.todosandboxkmm.di.repositoryModule
import com.gthio.todosandboxkmm.firebaseModule
import org.koin.dsl.module

val appModule = listOf(
    firebaseModule,
    repositoryModule,
)