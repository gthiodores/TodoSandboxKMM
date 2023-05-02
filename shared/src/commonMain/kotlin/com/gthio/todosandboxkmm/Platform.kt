package com.gthio.todosandboxkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform