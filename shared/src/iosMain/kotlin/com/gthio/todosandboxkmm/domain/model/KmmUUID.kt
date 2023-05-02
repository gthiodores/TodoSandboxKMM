package com.gthio.todosandboxkmm.domain.model

import platform.Foundation.NSUUID

actual class KmmUUID(private val uuid: NSUUID) {
    actual constructor() : this(NSUUID.UUID())

    actual val uuidString: String
        get() = uuid.UUIDString.lowercase()
    actual val hashValue: Int
        get() = uuid.hashCode()

    actual companion object {
        actual fun fromString(value: String): KmmUUID = KmmUUID(NSUUID(uUIDString = value))
    }
}