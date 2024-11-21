package com.sample.todo.core.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent


fun Context.safelyStartActivity(
    intent: Intent,
    onError: () -> Unit = {}
) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        onError()
    }
}