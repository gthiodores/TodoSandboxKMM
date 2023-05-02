package com.gthio.todosandboxkmm.ui.todo

import com.rickclephas.kmp.nativecoroutines.NativeFlow
import com.rickclephas.kmp.nativecoroutines.asNativeFlow
import kotlin.native.ObjCName

/**
 * This is the state of the TodoStore, which is a StateFlow.
 */
public val TodoComponent.uiStateFlow: NativeFlow<TodoState>
  get() = uiState.asNativeFlow(null)

/**
 * This is the state of the TodoStore, which is a StateFlow.
 */
@ObjCName(name = "uiState")
public val TodoComponent.uiStateValue: TodoState
  get() = uiState.value
