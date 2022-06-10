package com.tantei.todo.di

import android.content.Context
import androidx.room.Room
import com.tantei.todo.data.ToDoDataBase
import com.tantei.todo.data.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideTodoDao(database: ToDoDataBase): TodoDao {
        return database.toDoDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ToDoDataBase {
        return Room.databaseBuilder(
            appContext,
            ToDoDataBase::class.java,
            "todo_database"
        ).build()
    }
}