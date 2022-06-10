package com.tantei.todo.data

import android.app.Application
import androidx.room.Room
import com.tantei.todo.data.migration.Migration1To2

object DatabaseManager {
    private const val DB_NAME = "todo_database"
    private lateinit var application: Application

    val db: ToDoDataBase by lazy {
        if (application == null) {
            throw Exception("call DatabaseManager.withApplication first")
        }
        Room.databaseBuilder(application.applicationContext, ToDoDataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun withApplication(application: Application) {
        DatabaseManager.application = application
    }
}