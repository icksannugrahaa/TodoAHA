package com.icksan.todoaha.core.di

import androidx.room.Room
import com.icksan.todoaha.core.data.repository.MapsRepository
import com.icksan.todoaha.core.data.repository.TodoRepository
import com.icksan.todoaha.core.data.source.local.LocalDataSource
import com.icksan.todoaha.core.data.source.local.room.AHADatabase
import com.icksan.todoaha.core.data.source.remote.RemoteDataSource
import com.icksan.todoaha.core.data.source.remote.network.ApiService
import com.icksan.todoaha.core.domain.repository.IMapsRepository
import com.icksan.todoaha.core.domain.repository.ITodoRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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

    val networkModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        single<ITodoRepository> {
            TodoRepository(
                get()
            )
        }
        single<IMapsRepository> {
            MapsRepository(
                get()
            )
        }
    }
}