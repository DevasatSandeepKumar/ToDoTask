package com.sample.todo.data.mapper

import com.sample.todo.data.local.todo.entity.TodoEntity
import com.sample.todo.data.model.TodoModel

fun TodoEntity.toModel(): TodoModel {
    return TodoModel(
        id = _id,
        name = name,
        important = important,
        completed = completed,
        timestamp = timestamp
    )
}

fun TodoModel.toEntity(): TodoEntity {
    return TodoEntity(
        _id = id,
        name = name,
        important = important,
        completed = completed,
        timestamp = timestamp
    )
}