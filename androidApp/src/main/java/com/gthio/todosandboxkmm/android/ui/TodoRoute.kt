package com.gthio.todosandboxkmm.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gthio.todosandboxkmm.domain.model.ColorCode
import com.gthio.todosandboxkmm.domain.model.TodoItem
import com.gthio.todosandboxkmm.ui.todo.TodoComponent
import org.lighthousegames.logging.logging

@Composable
fun TodoRoute(
    onAbout: () -> Unit,
    todoComponent: TodoComponent,
) {
    val log = logging("TodoRoute")

    val uiState by todoComponent.uiState.collectAsState()

    // TODO: Fix issue with loading next page
    val listState = rememberLazyListState()

//    LaunchedEffect(listState) {
//        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9 }
//            .collect { index ->
//                log.d { "Collecting : $index" }
//                if (index >= uiState.items.items.size - 4 && uiState.items.loadStatus == PagingLoadStatus.Success) {
//                    todoComponent.loadNext()
//                }
//            }
//    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Todo List") },
                actions = {
                    IconButton(onClick = onAbout) {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = "About")
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TodoList(
                modifier = Modifier.weight(1f),
                lazyState = listState,
                onItemClicked = {
                    // Do Nothing For Now
                },
//                items = uiState.items.items,
                items = uiState.items,
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = uiState.title,
                onValueChange = todoComponent::onTitleUpdated,
                label = { Text("Title") },
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = uiState.body,
                onValueChange = todoComponent::onBodyUpdated,
                label = { Text("Description") },
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            ) {
                items(items = ColorCode.values().toList()) { colorCode ->
                    TodoColorButton(
                        onClick = todoComponent::onColorUpdated,
                        colorCode = colorCode,
                        isSelected = colorCode.code == uiState.colorCode.code,
                    )
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = todoComponent::onAddTodo,
            ) {
                Text("Add Todo")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TodoList(
    modifier: Modifier,
    lazyState: LazyListState = rememberLazyListState(),
    onItemClicked: (TodoItem) -> Unit,
    items: List<TodoItem>
) {
    LazyColumn(
        modifier = modifier,
        state = lazyState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(items) { item ->
            TodoItemView(onItemClicked = onItemClicked, item = item)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoItemView(
    onItemClicked: (TodoItem) -> Unit,
    item: TodoItem,
) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = { onItemClicked(item) },
        backgroundColor = Color(item.colorCode.toColorInt()),
    ) {
        ListItem(
            text = { Text(item.title) },
            secondaryText = { Text(item.content) },
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
fun TodoColorButton(
    modifier: Modifier = Modifier,
    onClick: (ColorCode) -> Unit,
    isSelected: Boolean,
    colorCode: ColorCode
) {
    Button(
        modifier = modifier,
        onClick = { onClick(colorCode) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(colorCode.toColorInt()),
            contentColor = Color.Black
        ),
    ) {
        if (isSelected) Text(text = "✓")
    } // Empty Button (No Text)
}