package com.sample.todo.presentation.screen.todo.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.todo.data.local.preferences.Preferences
import com.sample.todo.data.model.TodoModel
import com.sample.todo.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val preferences: Preferences
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    var todos: ImmutableList<TodoModel> by mutableStateOf(persistentListOf())
        private set

    var searchExpanded: Boolean by mutableStateOf(false)
        internal set


    var hideCompleted: Boolean by mutableStateOf(false)
        private set


    private val _event = Channel<TodoListEvent>()
    val event: Flow<TodoListEvent> = _event.receiveAsFlow()


    init {
        observe()
    }

    private fun observe() {
        observeQueryAndPreferences()
    }

    private fun observeQueryAndPreferences() {
        viewModelScope.launch {
            combine(query, preferences.preferences) { query, preferences ->
                Pair(query, preferences)
            }.flatMapLatest { (query, preferences) ->
                hideCompleted = preferences.hideCompleted
                repository.getTodos(
                    query = query,
                    hideCompleted = preferences.hideCompleted,
                    sort = preferences.sort
                )
            }.collectLatest { todos ->
                this@TodoListViewModel.todos = todos.toImmutableList()
            }
        }
    }

    fun onQueryChange(value: String) {
        viewModelScope.launch {
            _query.emit(value)
        }
    }


    fun onSearchExpanded() {
        searchExpanded = true
    }

    fun onSearchCollapsed() {
        viewModelScope.launch {
            _query.emit("")
            searchExpanded = false
        }
    }



    enum class TodoListEvent {
    }
}