pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("mvi-kotlin", "3.2.0")
            version("decompose", "2.0.0-alpha-02")
            version("arrow", "1.2.0-RC")
            version("native-coroutine", "1.0.0-ALPHA-7")
            version("firebase-kmm", "1.8.1")
            version("koin", "3.2.0")

            library("mvi-core", "com.arkivanov.mvikotlin", "mvikotlin").versionRef("mvi-kotlin")
            library("mvi-main", "com.arkivanov.mvikotlin", "mvikotlin-main").versionRef("mvi-kotlin")
            library("mvi-rx", "com.arkivanov.mvikotlin", "rx").versionRef("mvi-kotlin")
            library(
                "mvi-coroutines",
                "com.arkivanov.mvikotlin",
                "mvikotlin-extensions-coroutines"
            ).versionRef("mvi-kotlin")

            bundle("mvikotlin", listOf("mvi-core", "mvi-main", "mvi-rx", "mvi-coroutines"))

            library("decompose-core", "com.arkivanov.decompose", "decompose").versionRef("decompose")
            library(
                "decompose-jetpack",
                "com.arkivanov.decompose",
                "extensions-compose-jetpack"
            ).versionRef("decompose")

            library("arrow-core", "io.arrow-kt", "arrow-core").versionRef("arrow")
            library("arrow-coroutine", "io.arrow-kt", "arrow-fx-coroutines").versionRef("arrow")
            bundle("arrow", listOf("arrow-core", "arrow-coroutine"))

            library("kmm-firebase-common", "dev.gitlive", "firebase-common").versionRef("firebase-kmm")
            library("kmm-firebase-auth", "dev.gitlive", "firebase-auth").versionRef("firebase-kmm")
            library("kmm-firebase-firestore", "dev.gitlive", "firebase-firestore").versionRef("firebase-kmm")
            bundle("kmm-firebase-bundle", listOf("kmm-firebase-firestore", "kmm-firebase-common", "kmm-firebase-auth"))

            library("koin-core", "io.insert-koin", "koin-core").versionRef("koin")
            library("koin-test", "io.insert-koin", "koin-test").versionRef("koin")
            library("koin-android", "io.insert-koin", "koin-android").versionRef("koin")
            bundle("koin", listOf("koin-core", "koin-test", "koin-android"))
        }
    }
}

rootProject.name = "TodoSandboxKMM"
include(":androidApp")
include(":shared")