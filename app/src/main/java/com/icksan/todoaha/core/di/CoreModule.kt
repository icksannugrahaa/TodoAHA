package com.icksan.todoaha.core.di

import androidx.room.Room
import com.icksan.todoaha.core.data.repository.TodoRepository
import com.icksan.todoaha.core.data.source.local.LocalDataSource
import com.icksan.todoaha.core.data.source.local.room.AHADatabase
import com.icksan.todoaha.core.domain.repository.ITodoRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object CoreModule {
    val databaseModule = module {
        factory { get<AHADatabase>().TodoDao() }
        single {
            Room.databaseBuilder(
                androidContext(),
                AHADatabase::class.java, "AHA.db"
            ).fallbackToDestructiveMigration().build()
        }
    }

//    val networkModule = module {
//        single {
//            OkHttpClient.Builder()
//                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .connectTimeout(120, TimeUnit.SECONDS)
//                .readTimeout(120, TimeUnit.SECONDS)
//                .build()
//        }
//        single {
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(get())
//                .build()
//            retrofit.create(ApiService::class.java)
//        }
//    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
//        single { RemoteDataSource(get()) }
        single<ITodoRepository> {
            TodoRepository(
                get()
            )
        }
    }
}