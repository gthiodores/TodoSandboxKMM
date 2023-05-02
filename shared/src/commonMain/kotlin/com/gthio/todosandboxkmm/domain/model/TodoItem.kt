package com.gthio.todosandboxkmm.domain.model

data class TodoItem(
    val uuid: KmmUUID = KmmUUID(),
    val title: String = "",
    val content: String = "",
    val colorCode: ColorCode = ColorCode.WHITE,
    val tags: List<TodoTag> = listOf(),
)