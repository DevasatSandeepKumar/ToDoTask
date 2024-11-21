package com.sample.todo.presentation.screen.todo.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sample.todo.R
import com.sample.todo.presentation.component.button.TodoFloatingActionButton
import com.sample.todo.presentation.component.card.CardTodo
import com.sample.todo.presentation.component.text.TextPlain
import com.sample.todo.presentation.component.topbar.TopBar
import com.sample.todo.presentation.component.topbar.TopBarIconButton
import com.sample.todo.presentation.component.topbar.TopBarSearch
import com.sample.todo.presentation.navigation.MainNavGraph
import com.sample.todo.presentation.screen.destinations.TodoAddScreenDestination


@MainNavGraph(start = true)
@Destination
@Composable
fun TodoListScreen(
    snackbar: SnackbarHostState = remember { SnackbarHostState() },
    navigator: DestinationsNavigator,
    viewmodel: TodoListViewModel = hiltViewModel(),
) {

    Scaffold(
        topBar = {
            TopBar(
                title = {
                    if (viewmodel.searchExpanded) {
                        TopBarSearch(
                            query = viewmodel.query.collectAsState().value,
                            onQueryChange = viewmodel::onQueryChange,
                            onCancel = viewmodel::onSearchCollapsed
                        )
                    } else {
                        TextPlain(
                            id = R.string.tasks
                        )
                    }
                },
                actions = {
                    if (!viewmodel.searchExpanded) {
                        TopBarIconButton(
                            onClick = viewmodel::onSearchExpanded,
                            icon = Icons.Default.Search,
                            contentDescription = R.string.cd_search_button
                        )
                    }

                }
            )
        },
        floatingActionButton = {
            TodoFloatingActionButton(
                onClick = {
                    viewmodel.searchExpanded=false
                    navigator.navigate(TodoAddScreenDestination)
                },
                icon = Icons.Default.Add,
                contentDescription = R.string.cd_task_add
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbar
            ) { data ->
                Snackbar(
                    snackbarData = data
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = viewmodel.todos,
                key = { it.id }
            ) { todo ->
                CardTodo(
                    todo = todo
                )
            }
        }
    }
}