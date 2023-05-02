package com.gthio.todosandboxkmm.domain.model

import java.util.UUID

actual class KmmUUID(private val uuid: UUID) {
    actual constructor() : this(UUID.randomUUID())

    actual val uuidString: String
        get() = uuid.toString()
    actual val hashValue: Int
        get() = uuid.hashCode()

    actual companion object {
        actual fun fromString(value: String): KmmUUID = KmmUUID(UUID.fromString(value))
    }
}