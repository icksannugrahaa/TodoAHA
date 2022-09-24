package com.icksan.todoaha.main.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.domain.model.Todo
import com.icksan.todoaha.core.domain.usecase.TodoUseCase
import androidx.lifecycle.asLiveData

class TodoViewModel(private val todoUseCase: TodoUseCase) : ViewModel() {
    fun listTodo(): LiveData<Resource<List<Todo>>> = todoUseCase.listTodo().asLiveData()
    fun findTodo(search: String): LiveData<Resource<List<Todo>>> = todoUseCase.findTodo(search).asLiveData()
    fun storeTodo(todo: Todo): LiveData<Resource<Todo>> = todoUseCase.storeTodo(todo).asLiveData()
    fun updateTodo(todo: Todo): LiveData<Resource<Todo>> = todoUseCase.updateTodo(todo).asLiveData()
    fun deleteTodo(todo: Todo): LiveData<Resource<Todo>> = todoUseCase.removeTodo(todo).asLiveData()
}