package com.tantei.todo.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tantei.todo.data.DatabaseManager
import com.tantei.todo.data.ToDoDataBase
import com.tantei.todo.data.models.TodoData
import com.tantei.todo.data.repository.ToDoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = DatabaseManager.db.toDoDao()
    private val repository: ToDoRepository

    val getAllData: LiveData<List<TodoData>>

    init {
        repository = ToDoRepository(todoDao)
        getAllData = repository.getAllData
    }

    fun insertData(todoData: TodoData) {
        viewModelScope.launch {
            repository.insertData(todoData)
        }
    }
}