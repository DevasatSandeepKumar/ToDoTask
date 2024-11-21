package com.sample.todo.presentation.screen.todo.add


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sample.todo.R
import com.sample.todo.presentation.component.button.TodoFloatingActionButton
import com.sample.todo.presentation.component.dialog.TodoAlertDialog
import com.sample.todo.presentation.component.misc.ObserveEvent
import com.sample.todo.presentation.component.misc.TodoConfigure
import com.sample.todo.presentation.component.progressbar.CircularProgressBar
import com.sample.todo.presentation.component.text.TextPlain
import com.sample.todo.presentation.component.topbar.TopBar
import com.sample.todo.presentation.component.topbar.TopBarIconButton
import com.sample.todo.presentation.navigation.MainNavGraph
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@MainNavGraph
@Destination
@Composable
fun TodoAddScreen(
    snackbar: SnackbarHostState = remember { SnackbarHostState() },
    navigator: DestinationsNavigator,
    viewmodel: TodoAddViewModel = hiltViewModel()
) {

    var showDialog by remember { mutableStateOf(false) }

    ObserveEvent(
        flow = viewmodel.event,
        onEvent = { event ->
            when (event) {

                is TodoAddViewModel.TodoAddEvent.InvalidInput -> {
            }

                TodoAddViewModel.TodoAddEvent.TodoAdded -> {
                    viewmodel.onSuccess=true
                }


            }
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = {
                    TextPlain(
                        id = R.string.new_task
                    )
                },
                navigationIcon = {
                    TopBarIconButton(
                        onClick = navigator::popBackStack,
                        icon = Icons.Default.ArrowBack,
                        contentDescription = R.string.cd_back_button
                    )
                }
            )
        },

        floatingActionButton = {
            TodoFloatingActionButton(
                onClick = viewmodel::onSaveTodo,
                icon = Icons.Default.Done,
                contentDescription = R.string.cd_done_button
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
        },

    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            TodoConfigure(
                name = viewmodel.name,
                onNameChange = viewmodel::onNameChange
            )
            if (viewmodel.onError) {
                TodoAlertDialog(
                    title = R.string.error,
                    message = R.string.error_name,
                    onDismiss = viewmodel::onDismiss,
                )
            }
            if(viewmodel.onSuccess){
                CircularProgressBar(viewmodel.onSuccess)
                LaunchedEffect(Unit) {
                    delay(3.seconds)
                    showDialog=false
                    navigator.popBackStack()
                }

                }

            }
        }
    }

