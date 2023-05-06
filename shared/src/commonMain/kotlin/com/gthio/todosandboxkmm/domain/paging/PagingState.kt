package com.gthio.todosandboxkmm.domain.paging

data class PagingState<T>(
    val items: List<T> = emptyList(),
    val loadStatus: PagingLoadStatus = PagingLoadStatus.Initial,
)