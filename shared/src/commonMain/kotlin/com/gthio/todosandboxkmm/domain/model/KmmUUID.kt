package com.gthio.todosandboxkmm.domain.model

expect class KmmUUID() {
    val uuidString: String

    val hashValue: Int

    companion object {
        fun fromString(value: String): KmmUUID
    }
}