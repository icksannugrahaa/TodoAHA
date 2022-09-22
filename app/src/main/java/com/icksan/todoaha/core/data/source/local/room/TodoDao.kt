package com.icksan.todoaha.core.data.source.local.room

import androidx.room.*
import com.icksan.todoaha.core.data.source.local.entitiy.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos")
    fun listTodo(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE title = :search")
    fun findTodo(search: String): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: TodoEntity)

    @Update
    fun updateTodo(todo: TodoEntity)

    @Delete
    fun deleteTodo(todo: TodoEntity)
}