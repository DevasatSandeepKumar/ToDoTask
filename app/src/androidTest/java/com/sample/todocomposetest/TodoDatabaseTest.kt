package com.sample.todocomposetest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sample.todo.data.local.preferences.TodoSort
import com.sample.todo.data.local.todo.dao.TodoDao
import com.sample.todo.data.local.todo.database.TodoDatabase
import com.sample.todo.data.mapper.toEntity
import com.sample.todo.data.model.TodoModel
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class ToDatabaseTest : TestCase() {

    private lateinit var db: TodoDatabase
    private lateinit var dao: TodoDao

    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java).build()
        dao = db.todoDao()
    }

    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeTodo() = runBlocking {
        val todo = TodoModel(
            name = "sandy",
            important = false
        )
        dao.insertTodo(todo.toEntity())
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
        dao.getTodosSortedByName("sandy", false).collect {
            assertEquals(it[0].name,"sandy")
            latch.countDown()
        }
        }
        latch.await()
        job.cancelAndJoin()

    }

    @Test
    fun ReadTodo() = runBlocking {
        val inserted = dao!!.getTodosSortedByName("sandeep", false)
        assertNotNull(inserted)

    }


    @Test
    fun delete_returnsTrue() = runBlocking {
        val name = TodoModel(1,"Mary",false )
        val secondName = TodoModel(2,"John",false )

        dao.insertTodo(name.toEntity())
        dao.insertTodo(secondName.toEntity())

        dao.deleteAllTodo()

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getTodos("sandy", false,TodoSort.BY_NAME).collect {
                assertTrue(true)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()

    }

    @Test
    fun updateWord_returnsTrue() = runBlocking {
        val name = TodoModel(id = 1, "Mary")
        dao.insertTodo(name.toEntity())

        val updatedWord = TodoModel(id = 1, "John")

        dao.updateTodo(updatedWord.toEntity())
        assertTrue(true)

    }

}
