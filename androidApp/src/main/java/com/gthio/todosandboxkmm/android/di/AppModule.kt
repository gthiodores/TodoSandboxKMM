package com.gthio.todosandboxkmm.android.di

import com.gthio.todosandboxkmm.di.repositoryModule
import com.gthio.todosandboxkmm.firebaseModule

val appModule = listOf(
    firebaseModule,
    repositoryModule,
)