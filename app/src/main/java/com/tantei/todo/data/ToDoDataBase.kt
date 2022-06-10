package com.tantei.todo.data

import androidx.room.*

import com.tantei.todo.data.converter.PriorityConverter
import com.tantei.todo.data.converter.StateConverter
import com.tantei.todo.data.models.TodoData

@Database(entities = [TodoData::class], version = 2, exportSchema = false)
@TypeConverters(PriorityConverter::class, StateConverter::class)
abstract class ToDoDataBase : RoomDatabase() {
    abstract fun toDoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDataBase? = null
    }
}