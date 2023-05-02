package com.gthio.todosandboxkmm.android

import android.app.Application
import com.gthio.todosandboxkmm.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@TodoApp)
            modules(appModule)
        }
    }
}