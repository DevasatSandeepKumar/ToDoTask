package com.sample.todo.data.local.todo.constant

object TodoDatabaseConst {

    /**
     * Database name.
     */
    const val NAME = "TodoListDatabase"

    /**
     * Database version.
     */
    const val VERSION = 1

    /**
     * Table ToDo.
     * Entity -> [com.sample.todo.data.local.todo.entity.TodoEntity]
     * Model -> [com.sample.todo.data.model.TodoModel]
     */
    const val TABLE_TODO = "task_table"
}