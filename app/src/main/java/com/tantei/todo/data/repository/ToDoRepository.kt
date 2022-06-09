package com.tantei.todo.data.repository

import androidx.lifecycle.LiveData
import com.tantei.todo.data.TodoDao
import com.tantei.todo.data.models.TodoData

class ToDoRepository(private val todoDao: TodoDao) {

    val getAllData: LiveData<List<TodoData>> = todoDao.getAllData()

    suspend fun insertData(todoData: TodoData) {
        todoDao.insertData(todoData)
    }
}