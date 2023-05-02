package com.gthio.todosandboxkmm

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize
import org.koin.dsl.module
import cocoapods.FirebaseCore.*

val firebaseModule = module {
    single {
        Firebase.initialize()
        Firebase.firestore
    }
}