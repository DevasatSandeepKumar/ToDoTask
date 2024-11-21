package com.sample.todo.presentation.component.dialog

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sample.todo.R
import com.sample.todo.presentation.theme.backgroundDialogButton

@Composable
fun TodoAlertDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = title)
            )
        },
        text = {
            Text(
                text = stringResource(id = message)
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(id = R.string.yes),
                    color = MaterialTheme.colorScheme.backgroundDialogButton()
                )
            }
        }
    )
}