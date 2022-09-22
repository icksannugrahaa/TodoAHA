package com.icksan.todoaha.core.data.repository

import com.icksan.todoaha.core.data.LocalOnlyBoundResources
import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.data.source.local.LocalDataSource
import com.icksan.todoaha.core.data.source.local.entitiy.TodoEntity
import com.icksan.todoaha.core.domain.model.Todo
import com.icksan.todoaha.core.domain.repository.ITodoRepository
import com.icksan.todoaha.core.utils.DataMapper
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

    override fun findTodo(search: String): Flow<Resource<List<Todo>>> =
        object: LocalOnlyBoundResources<List<Todo>, List<TodoEntity>>() {
            override fun loadFromDB(): Flow<List<Todo>> = flow {
                localDataSource.findTodo(search).map { DataMapper.mapTodoToListModel(it) }.toList()
            }
        }.asFlow()

    override fun storeTodo(todo: Todo): Flow<Resource<String>> =
        object: LocalOnlyBoundResources<String, Todo>() {
            override fun loadFromDB(): Flow<String> = flow {
                localDataSource.insertTodo(DataMapper.mapTodoToEntity(todo))
            }
        }.asFlow()

    override fun updateTodo(todo: Todo): Flow<Resource<String>> =
        object: LocalOnlyBoundResources<String, Todo>() {
            override fun loadFromDB(): Flow<String> = flow {
                localDataSource.updateTodo(DataMapper.mapTodoToEntity(todo))
            }
        }.asFlow()

    override fun removeTodo(todo: Todo): Flow<Resource<String>> =
        object: LocalOnlyBoundResources<String, Todo>() {
            override fun loadFromDB(): Flow<String> = flow {
                localDataSource.deleteTodo(DataMapper.mapTodoToEntity(todo))
            }
        }.asFlow()
}