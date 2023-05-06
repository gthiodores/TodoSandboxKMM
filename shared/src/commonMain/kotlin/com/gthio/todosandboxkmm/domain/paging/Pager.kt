package com.gthio.todosandboxkmm.domain.paging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.lighthousegames.logging.logging

// TODO: Optimize so load data will also search for the next key
class Pager<K, V>(
    private val initialKey: K,
    private val nextKey: suspend (K) -> K?,
    private val loadData: suspend (K) -> List<V>,
) {
    private val mutex = Mutex()

    private val _pagingState = MutableStateFlow(PagingState<V>())
    val state: Flow<PagingState<V>> = _pagingState

    private val _currentKey: MutableStateFlow<K> = MutableStateFlow(initialKey)
    private val logger = logging("Pager")

    suspend fun loadNext() {
        mutex.withLock {
            val currentPageState = _pagingState.value
            val currentStatus = currentPageState.loadStatus

            if (currentStatus == PagingLoadStatus.Loading || currentStatus == PagingLoadStatus.Exhausted) {
                return
            }

            logger.d { "Loading next page" }

            if (currentStatus == PagingLoadStatus.Initial) {
                load()
                return
            }

            val nextKey = nextKey(_currentKey.value)

            nextKey?.let { key ->
                _currentKey.updateAndGet { key }
                load(key)
                return
            }

            _pagingState.update { old -> old.copy(loadStatus = PagingLoadStatus.Exhausted) }
        }
    }

    suspend fun reset() {
        mutex.withLock {
            logger.d { "Loading from initial page" }
            _currentKey.update { initialKey }
            load(initialKey)
        }
    }

    suspend fun retry() {
        mutex.withLock {
            logger.d { "Reloading current page" }
            load()
        }
    }

    private suspend fun load(key: K? = _currentKey.value) {
        if (key == null) return
        _pagingState.update { old -> old.copy(loadStatus = PagingLoadStatus.Loading) }
        try {
            val items = loadData(key)
            _pagingState.update { old ->
                old.copy(
                    items = old.items + items,
                    loadStatus = if (items.isEmpty()) PagingLoadStatus.Exhausted else PagingLoadStatus.Success,
                )
            }
        } catch (e: Throwable) {
            _pagingState.update { old -> old.copy(loadStatus = PagingLoadStatus.Error(e)) }
        }
    }
}