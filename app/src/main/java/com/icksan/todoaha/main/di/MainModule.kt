package com.icksan.todoaha.main.di

import com.icksan.todoaha.core.domain.usecase.TodoIterator
import com.icksan.todoaha.core.domain.usecase.TodoUseCase
import com.icksan.todoaha.main.todo.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    val useCaseModule = module {
        factory<TodoUseCase> { TodoIterator(get()) }
    }

    val viewModelModule = module {
        viewModel { TodoViewModel(get()) }
//        viewModel { AuthenticationViewModel(get()) }
//        viewModel { UploadViewModel(get()) }
//        viewModel { PredictViewModel(get()) }
    }
}