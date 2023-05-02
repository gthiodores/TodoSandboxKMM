package com.gthio.todosandboxkmm

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single {
        Firebase.initialize(androidContext())
        Firebase.firestore
    }
}