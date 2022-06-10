package com.tantei.todo.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tantei.todo.data.DatabaseManager
import com.tantei.todo.data.ToDoDataBase
import com.tantei.todo.data.models.TodoData
import com.tantei.todo.data.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    application: Application,
    private val repository: ToDoRepository
) : AndroidViewModel(application) {
    private val todoDao = DatabaseManager.db.toDoDao()


    val getAllData: LiveData<List<TodoData>>
    val sortByHighPriority: LiveData<List<TodoData>>
    val sortByLowPriority: LiveData<List<TodoData>>

    init {
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(todoData: TodoData) {
        viewModelScope.launch {
            repository.insertData(todoData)
        }
    }
    fun updateData(todoData: TodoData) {
        viewModelScope.launch {
            repository.updateData(todoData)
        }
    }
    fun deleteData(todoData: TodoData) {
        viewModelScope.launch {
            repository.deleteData(todoData)
        }
    }
    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
    fun searchDatabase(searchQuery: String): LiveData<List<TodoData>> {
        return repository.searchDatabase(searchQuery)
    }
}