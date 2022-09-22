package com.icksan.core.domain.usecase

import com.icksan.core.data.Resource
import com.icksan.core.domain.model.Todo
import com.icksan.core.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow

class TodoIterator(private val repository: ITodoRepository) : TodoUseCase {
    override fun listTodo(): Flow<Resource<List<Todo>>> = repository.listTodo()
}