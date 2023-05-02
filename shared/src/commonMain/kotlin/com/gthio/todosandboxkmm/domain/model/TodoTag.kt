package com.gthio.todosandboxkmm.domain.model

data class TodoTag(
    val uuid: KmmUUID = KmmUUID(),
    val name: String = "",
)