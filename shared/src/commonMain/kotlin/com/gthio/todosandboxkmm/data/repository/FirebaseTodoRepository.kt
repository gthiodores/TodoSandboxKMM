package com.gthio.todosandboxkmm.data.repository

import arrow.core.Either
import com.gthio.todosandboxkmm.data.model.FirestoreTodo
import com.gthio.todosandboxkmm.domain.model.TodoItem
import com.gthio.todosandboxkmm.domain.repository.DuplicateTodo
import com.gthio.todosandboxkmm.domain.repository.TodoNotFound
import com.gthio.todosandboxkmm.domain.repository.TodoRepository
import com.gthio.todosandboxkmm.domain.util.DispatcherProvider
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreException
import dev.gitlive.firebase.firestore.FirestoreExceptionCode
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.code
import dev.gitlive.firebase.firestore.startAfter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * FirestoreTodoRepository is an implementation of TodoRepository.
 * It uses Firestore as a backend.
 *
 * @param firestore is a Firestore instance.
 * @param dispatcher is a DispatcherProvider instance.
 */
class FirebaseTodoRepository(
    private val firestore: FirebaseFirestore,
    private val dispatcher: DispatcherProvider,
) : TodoRepository {

    override fun observeTodoList(): Flow<List<TodoItem>> {
        return firestore
            .collection("todo")
            .snapshots
            .map { snapshot -> snapshot.documents }
            .map { documents -> documents.map { item -> item.data(FirestoreTodo.serializer()) } }
            .map { todos -> todos.map { item -> item.toDomain() } }
            .flowOn(dispatcher.io())
            .conflate()
    }

//    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
//    override fun observeTodoPaging(scope: CoroutineScope): Flow<PagingData<TodoItem>> {
//        return Pager(
//            clientScope = scope,
//            config = PagingConfig(pageSize = 5, prefetchDistance = 2, initialLoadSize = 10),
//            initialKey = firestore.collection("todo").limit(10),
//            getItems = { key, _ ->
//                val documents = key.get().documents
//                val items = documents
//                    .map { snapshot -> snapshot.data(FirestoreTodo.serializer()) }
//                    .map { item -> item.toDomain() }
//
//                val lasDocument = documents.lastOrNull()
//
//                val nextKey = lasDocument?.let { document ->
//                    firestore
//                        .collection("todo")
//                        .limit(5)
//                        .startAfter(document)
//                }
//
//                PagingResult(
//                    items = items,
//                    currentKey = key,
//                    prevKey = { null },
//                    nextKey = { nextKey },
//                )
//            }
//        )
//            .pagingData
//            .cachedIn(scope)
//    }

    override fun observeCustomPaging(): com.gthio.todosandboxkmm.domain.paging.Pager<Query, TodoItem> {
        return com.gthio.todosandboxkmm.domain.paging.Pager(
            initialKey = firestore.collection("todo").limit(10),
            nextKey = { oldKey ->
                withContext(dispatcher.io()) {
                    oldKey.get().documents.lastOrNull()?.let { lastDocument ->
                        firestore
                            .collection("todo")
                            .limit(5)
                            .startAfter(lastDocument)
                    }
                }
            },
            loadData = { key ->
                withContext(dispatcher.io()) {
                    key
                        .get()
                        .documents
                        .map { snapshot -> snapshot.data(FirestoreTodo.serializer()) }
                        .map { item -> item.toDomain() }
                }
            }
        )
    }

    override suspend fun addTodoItem(todoItem: TodoItem): Either<DuplicateTodo, Unit> {
        return Either
            .catchOrThrow<FirebaseFirestoreException, Unit> { firestoreAddTodo(todoItem) }
            .mapLeft { e ->
                if (e.code == FirestoreExceptionCode.ALREADY_EXISTS) DuplicateTodo(todoItem)
                else throw e
            }
    }

    private suspend fun firestoreAddTodo(item: TodoItem) = withContext(dispatcher.io()) {
        firestore
            .collection("todo")
            .document(item.uuid.uuidString)
            .set(FirestoreTodo.serializer(), FirestoreTodo.from(item))
    }

    override suspend fun removeTodoItem(todoItem: TodoItem): Either<TodoNotFound, Unit> {
        return Either
            .catchOrThrow<FirebaseFirestoreException, Unit> { firestoreRemoveTodo(todoItem) }
            .mapLeft { e ->
                if (e.code == FirestoreExceptionCode.NOT_FOUND) TodoNotFound
                else throw e
            }
    }

    private suspend fun firestoreRemoveTodo(item: TodoItem) = withContext(dispatcher.io()) {
        firestore
            .collection("todo")
            .document(item.uuid.uuidString)
            .delete()
    }
}