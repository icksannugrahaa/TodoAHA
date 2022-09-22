package com.icksan.core.data.repository

import com.icksan.core.data.LocalOnlyBoundResources
import com.icksan.core.data.Resource
import com.icksan.core.data.source.local.LocalDataSource
import com.icksan.core.data.source.local.entitiy.TodoEntity
import com.icksan.core.domain.model.Todo
import com.icksan.core.domain.repository.ITodoRepository
import com.icksan.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class TodoRepository (
    private val localDataSource: LocalDataSource
) : ITodoRepository {
    override fun listTodo(): Flow<Resource<List<Todo>>> =
        object: LocalOnlyBoundResources<List<Todo>, List<TodoEntity>>() {
                override fun loadFromDB(): Flow<List<Todo>> = flow {
                    localDataSource.listTodo().map { DataMapper.mapTodoToListModel(it) }.toList()
                }
        }.asFlow()

}