package com.gthio.todosandboxkmm.domain.paging

/**
 * A sealed interface that signals the current state of the pager
 */
sealed interface PagingLoadStatus {
    /**
     *  Initial state where no data is loaded
     */
    object Initial : PagingLoadStatus

    /**
     *  Fetching data from the paging source
     */
    object Loading : PagingLoadStatus

    /**
     * Encountered an error while fetching data from paging source
     */
    data class Error<R>(val err: R) : PagingLoadStatus

    /**
     * Succeeded in fetching data
     */
    object Success : PagingLoadStatus

    /**
     *  Successfully fetched data but the dataset is empty
     */
    object Exhausted : PagingLoadStatus

    /**
     * A helper function to filter exhausted and loading state
     */
    fun isAvailable() : Boolean {
        if (this is Exhausted || this is Loading) return false

        return true
    }
}