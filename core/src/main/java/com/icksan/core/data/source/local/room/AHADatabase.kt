package com.icksan.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.icksan.core.data.source.local.entitiy.TodoEntity

@Database(entities = [TodoEntity::class],
    version = 1,
    exportSchema = false)
abstract class AHADatabase : RoomDatabase() {
    abstract fun TodoDao(): TodoDao

    companion object {

        @Volatile
        private var INSTANCE: AHADatabase? = null

        fun getInstance(context: Context): AHADatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AHADatabase::class.java,
                    "AHA.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}