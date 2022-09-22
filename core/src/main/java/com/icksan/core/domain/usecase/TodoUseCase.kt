package com.icksan.core.domain.usecase

import com.icksan.core.data.Resource
import com.icksan.core.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoUseCase {
    fun listTodo(): Flow<Resource<List<Todo>>>
}