package com.icksan.todoaha.core.domain.usecase

import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.domain.model.Todo
import com.icksan.todoaha.core.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow

class TodoIterator(private val repository: ITodoRepository) : TodoUseCase {
    override fun listTodo(): Flow<Resource<List<Todo>>> = repository.listTodo()
    override fun findTodo(search: String): Flow<Resource<List<Todo>>> = repository.findTodo(search)
    override fun storeTodo(todo: Todo): Flow<Resource<String>> = repository.storeTodo(todo)
    override fun updateTodo(todo: Todo): Flow<Resource<String>> = repository.updateTodo(todo)
    override fun removeTodo(todo: Todo): Flow<Resource<String>> = repository.removeTodo(todo)
}