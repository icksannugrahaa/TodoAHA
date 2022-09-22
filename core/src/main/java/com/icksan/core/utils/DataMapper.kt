package com.icksan.core.utils

import com.icksan.core.data.source.local.entitiy.TodoEntity
import com.icksan.core.domain.model.Todo

object DataMapper {
    fun mapTodoToEntity(data: Todo) = TodoEntity(
        id = data.id,
        title = data.title,
        description = data.description,
        createdAt = data.createdAt,
        updatedAt = data.updatedAt
    )

    fun mapTodoToModel(data: TodoEntity) = Todo(
            id = data.id,
            title = data.title,
            description = data.description,
            createdAt = data.createdAt,
            updatedAt = data.updatedAt
        )

    fun mapTodoToListModel(data: List<TodoEntity>) = data.map {
        Todo(
            id = it.id,
            title = it.title,
            description = it.description,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )
    }
}