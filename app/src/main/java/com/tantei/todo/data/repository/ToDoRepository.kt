package com.tantei.todo.data.repository

import androidx.lifecycle.LiveData
import com.tantei.todo.data.TodoDao
import com.tantei.todo.data.models.State
import com.tantei.todo.data.models.TodoData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    val getAllData: LiveData<List<TodoData>> = todoDao.getAllData()
    val sortByHighPriority: LiveData<List<TodoData>> = todoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<TodoData>> = todoDao.sortByLowPriority()
    fun getDataByStates(vararg states: State): LiveData<List<TodoData>> {
        return todoDao.getAllDataByStates(states.map { it.code })
    }

    suspend fun insertData(todoData: TodoData) {
        todoDao.insertData(todoData)
    }

    suspend fun updateData(todoData: TodoData) {
        todoDao.updateData(todoData)
    }

    suspend fun fakeDeleteData(todoData: TodoData) {
        todoData.state = State.DELETED
        todoDao.updateData(todoData)
    }

    suspend fun deleteData(todoData: TodoData) {
        todoDao.deleteData(todoData)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<TodoData>> {
        return todoDao.searchDatabase(searchQuery)
    }
}