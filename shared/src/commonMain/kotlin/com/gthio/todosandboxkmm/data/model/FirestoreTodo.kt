package com.gthio.todosandboxkmm.data.model

import com.gthio.kmm_firestore_todo.data.model.FirestoreTodoTag
import com.gthio.todosandboxkmm.domain.model.ColorCode
import com.gthio.todosandboxkmm.domain.model.KmmUUID
import com.gthio.todosandboxkmm.domain.model.TodoItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * FirestoreTodo is a data class that represents a todo item in Firestore.
 * It is used to serialize/deserialize Firestore data.
 * It is also used to convert Firestore data to domain data.
 *
 * @param uuid is the UUID of a todo item.
 * @param title is the title of a todo item.
 * @param content is the content of a todo item.
 * @param colorCode is the color code of a todo item.
 * @param tags is a list of tags of a todo item.
 *
 * @see TodoItem
 * @see FirestoreTodoTag
 * @see ColorCode
 * @see KmmUUID
 */
@Serializable
data class FirestoreTodo(
    val uuid: String = "",
    val title: String = "",
    val content: String = "",
    @SerialName("color_code") val colorCode: Int = 0,
    val tags: List<FirestoreTodoTag> = listOf(),
) {
    fun toDomain(): TodoItem = TodoItem(
        uuid = KmmUUID.fromString(uuid),
        title = title,
        content = content,
        colorCode = ColorCode.fromCode(colorCode),
        tags = tags.map(FirestoreTodoTag::toDomain),
    )

    companion object {
        fun from(domain: TodoItem): FirestoreTodo {
            return FirestoreTodo(
                uuid = domain.uuid.uuidString,
                title = domain.title,
                content = domain.content,
                colorCode = domain.colorCode.code,
                tags = domain.tags.map(FirestoreTodoTag.Companion::from)
            )
        }
    }
}