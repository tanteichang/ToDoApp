package com.tantei.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tantei.todo.data.models.State
import com.tantei.todo.data.models.TodoData

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table WHERE state IN (:states) ORDER BY id ASC")
    fun getAllDataByStates(states: List<Byte>): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    fun getDataById(id: Int): LiveData<TodoData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todoData: TodoData)

    @Update
    suspend fun updateData(todoData: TodoData)

    @Delete
    suspend fun deleteData(todoData: TodoData)


    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<TodoData>>
}