package com.icksan.todoaha.core.data.source.local

import com.icksan.todoaha.core.data.source.local.entitiy.TodoEntity
import com.icksan.todoaha.core.data.source.local.room.TodoDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val dao: TodoDao) {

    fun listTodo(): Flow<List<TodoEntity>> = dao.listTodo()
    fun findTodo(search: String): Flow<List<TodoEntity>> = dao.findTodo(search)
    suspend fun insertTodo(todo: TodoEntity) = dao.insertTodo(todo)
    fun updateTodo(todo: TodoEntity) = dao.updateTodo(todo)
    fun deleteTodo(todo: TodoEntity) = dao.deleteTodo(todo)
}
