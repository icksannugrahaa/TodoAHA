package com.icksan.core.domain.repository

import com.icksan.core.data.Resource
import com.icksan.core.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {
    fun listTodo(): Flow<Resource<List<Todo>>>
}