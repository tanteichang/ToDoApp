package com.tantei.todo

import android.app.Application
import com.tantei.todo.data.DatabaseManager

class ToDoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        DatabaseManager.withApplication(this)
    }
}