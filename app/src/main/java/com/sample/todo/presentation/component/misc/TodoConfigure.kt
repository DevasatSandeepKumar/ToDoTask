package com.sample.todo.presentation.component.misc



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.todo.presentation.component.textfield.TodoTextField

@Composable
fun TodoConfigure(
    name: String,
    onNameChange: (name: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TodoTextField(
            value = name,
            onValueChange = onNameChange
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )

    }
}