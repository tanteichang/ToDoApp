package com.tantei.todo.database

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tantei.todo.data.*
import com.tantei.todo.data.models.Priority
import com.tantei.todo.data.models.State
import com.tantei.todo.data.models.TodoData
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule

private const val TAG = "ToDoDataBaseTest"

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class ToDoDataBaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var todoDao: TodoDao
    private lateinit var db: ToDoDataBase

    private val todoData1 = TodoData(1, "atitle", Priority.LOW, "desc", State.NEW)
    private val todoData2 = TodoData(2, "bbtitle", Priority.MEDIUM, "desc", State.DELETED)
    private val todoData3 = TodoData(3, "ccctitle", Priority.HIGH, "desc", State.DONE)


    @Before
    fun createDb() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ToDoDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        todoDao = db.toDoDao()
        todoDao.insertData(todoData1)
        todoDao.insertData(todoData2)
        todoDao.insertData(todoData3)
    }
    @After
    @Throws(IOException::class)
    fun closeDb() = runTest {
        todoDao.deleteAll()
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun getById() = runTest {
        val byID = todoDao.getDataById(1).getOrAwaitValue()
        assertThat(byID, equalTo(todoData1))
        val allData = todoDao.getAllData().getOrAwaitValue()
        assertThat(allData.size, equalTo(3))
    }
    @Test
    @Throws(Exception::class)
    fun getAll() = runTest {
        val allData = todoDao.getAllData().getOrAwaitValue()
        assertThat(allData.size, equalTo(3))
    }
    @Test
    @Throws(Exception::class)
    fun sortByHighPriority() = runTest {
        val list = todoDao.sortByHighPriority().getOrAwaitValue()
        assertThat(list.size, equalTo(3))
        assertThat(list.get(0), equalTo(todoData3))
    }
    @Test
    @Throws(Exception::class)
    fun sortByLowPriority() = runTest {
        val list = todoDao.sortByLowPriority().getOrAwaitValue()
        assertThat(list.size, equalTo(3))
        assertThat(list.get(0), equalTo(todoData1))
    }


    @Test
    @Throws(Exception::class)
    fun updateData() = runTest {
        todoDao.updateData(TodoData(1, "newTitle", Priority.HIGH, "newDesc"))
        val byID = todoDao.getDataById(1).getOrAwaitValue()
        assertThat(byID.title, equalTo("newTitle"))
    }


    @Test
    @Throws(Exception::class)
    fun deleteData() = runTest {
        todoDao.deleteData(todoData1)
        val byID = todoDao.getDataById(1).getOrAwaitValue()
        assertThat(byID, equalTo(null))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllData() = runTest {
        todoDao.deleteAll()
        val byID = todoDao.getDataById(2).getOrAwaitValue()
        assertThat(byID, equalTo(null))
    }
}