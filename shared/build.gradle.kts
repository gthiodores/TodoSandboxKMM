plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.8.20"
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.8.20-1.0.10" apply (true)
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-7" apply (true)
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.1"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")
        pod("Firebase")
        pod("Firebase/Auth")
        pod("Firebase/Firestore")
        pod("KMPNativeCoroutinesAsync", "1.0.0-ALPHA-7")
        pod("KMPNativeCoroutinesCombine", "1.0.0-ALPHA-7")
        framework {
            baseName = "shared"
            export(libs.decompose.core)
            export("com.arkivanov.essenty:lifecycle:1.1.0")
        }
    }
    
    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        val commonMain by getting {
            dependencies {
                api(libs.bundles.mvikotlin)
                api(libs.decompose.core)
                api(libs.bundles.arrow)
                api(libs.bundles.kmm.firebase.bundle)
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
                api(libs.koin.core)
                api("org.lighthousegames:logging:1.3.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.decompose.jetpack)
                api(libs.koin.android)
                api(platform("com.google.firebase:firebase-bom:31.5.0"))
                api("com.google.firebase:firebase-common")
                api("com.google.firebase:firebase-auth")
                api("com.google.firebase:firebase-firestore")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

dependencies {
    add("kspIosX64", "com.rickclephas.kmp:kmp-nativecoroutines-ksp:1.0.0-ALPHA-7")
    add("kspIosArm64", "com.rickclephas.kmp:kmp-nativecoroutines-ksp:1.0.0-ALPHA-7")
    add("kspIosSimulatorArm64", "com.rickclephas.kmp:kmp-nativecoroutines-ksp:1.0.0-ALPHA-7")
}

android {
    namespace = "com.gthio.todosandboxkmm"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
}