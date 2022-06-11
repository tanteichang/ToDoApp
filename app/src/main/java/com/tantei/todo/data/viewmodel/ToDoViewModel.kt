package com.tantei.todo.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tantei.todo.data.models.State
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

    val getAllData: LiveData<List<TodoData>>
    val sortByHighPriority: LiveData<List<TodoData>>
    val sortByLowPriority: LiveData<List<TodoData>>

    init {
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun getDataByStates(vararg states: State): LiveData<List<TodoData>> {
        return repository.getDataByStates(*states)
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

    fun fakeDeleteData(todoData: TodoData) {
        viewModelScope.launch {
            repository.fakeDeleteData(todoData)
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