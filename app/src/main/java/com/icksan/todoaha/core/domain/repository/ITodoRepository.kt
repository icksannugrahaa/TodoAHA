package com.icksan.todoaha.core.domain.repository

import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {
    fun listTodo(): Flow<Resource<List<Todo>>>
    fun findTodo(search: String): Flow<Resource<List<Todo>>>
    fun storeTodo(todo: Todo): Flow<Resource<Todo>>
    fun updateTodo(todo: Todo): Flow<Resource<Todo>>
    fun removeTodo(todo: Todo): Flow<Resource<Todo>>
}