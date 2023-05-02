package com.gthio.kmm_firestore_todo.data.model

import com.gthio.todosandboxkmm.domain.model.KmmUUID
import com.gthio.todosandboxkmm.domain.model.TodoTag
import kotlinx.serialization.Serializable

/**
 * FirestoreTodoTag is a data class that represents a todo tag in Firestore.
 * It is used to serialize/deserialize Firestore data.
 * It is also used to convert Firestore data to domain data.
 *
 * @param uuid is the UUID of a todo tag.
 * @param name is the name of a todo tag.
 *
 * @see TodoTag
 * @see KmmUUID
 */
@Serializable
data class FirestoreTodoTag(
    val uuid: String = "",
    val name: String = "",
) {
    fun toDomain(): TodoTag = TodoTag(uuid = KmmUUID.fromString(uuid), name = name)

    companion object {
        fun from(domain: TodoTag): FirestoreTodoTag {
            return FirestoreTodoTag(uuid = domain.uuid.uuidString, name = domain.name)
        }
    }
}