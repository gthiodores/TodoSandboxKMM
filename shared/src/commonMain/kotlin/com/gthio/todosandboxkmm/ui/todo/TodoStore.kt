package com.gthio.todosandboxkmm.ui.todo

import com.arkivanov.mvikotlin.core.store.Store

// An interface of a store for todo lists
internal interface TodoStore : Store<TodoIntent, TodoState, Nothing>