package com.icksan.todoaha.main.di

import com.icksan.todoaha.core.domain.usecase.MapsIterator
import com.icksan.todoaha.core.domain.usecase.MapsUseCase
import com.icksan.todoaha.core.domain.usecase.TodoIterator
import com.icksan.todoaha.core.domain.usecase.TodoUseCase
import com.icksan.todoaha.main.maps.MapsViewModel
import com.icksan.todoaha.main.todo.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    val useCaseModule = module {
        factory<TodoUseCase> { TodoIterator(get()) }
        factory<MapsUseCase> { MapsIterator(get()) }
    }

    val viewModelModule = module {
        viewModel { TodoViewModel(get()) }
        viewModel { MapsViewModel(get()) }
    }
}