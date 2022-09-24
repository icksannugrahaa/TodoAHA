package com.icksan.todoaha

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.icksan.todoaha.core.di.CoreModule.databaseModule
import com.icksan.todoaha.core.di.CoreModule.networkModule
import com.icksan.todoaha.core.di.CoreModule.repositoryModule
import com.icksan.todoaha.main.di.MainModule.useCaseModule
import com.icksan.todoaha.main.di.MainModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application()  {

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(base)
//        MultiDex.install(this)
//    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}