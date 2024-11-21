package com.sample.todo.injection.provide

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import com.sample.todo.R
import com.sample.todo.data.local.preferences.Preferences
import com.sample.todo.data.local.todo.callback.TodoCallback
import com.sample.todo.data.local.todo.constant.TodoDatabaseConst
import com.sample.todo.data.local.todo.dao.TodoDao
import com.sample.todo.data.local.todo.database.TodoDatabase
import com.sample.todo.data.repository.TodoRepository
import com.sample.todo.injection.name.ApplicationScope
import java.text.DateFormatSymbols
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(
        application: Application,
        todoCallback: TodoCallback
    ): TodoDatabase = Room.databaseBuilder(
        context = application,
        klass = TodoDatabase::class.java,
        name = TodoDatabaseConst.NAME
    ).addMigrations()
        .addCallback(todoCallback)
        .build()

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Provides
    @Singleton
    fun provideTodoCallback(
        todoDatabase: Provider<TodoDatabase>,
        @ApplicationScope applicationScope: CoroutineScope
    ): TodoCallback = TodoCallback(
        db = todoDatabase,
        scope = applicationScope
    )

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository = TodoRepository(todoDao)

    @Provides
    @Singleton
    fun providePreferences(application: Application): Preferences = Preferences(application)

    @Provides
    @Singleton
    fun provideDateFormatSymbols(application: Application): DateFormatSymbols = DateFormatSymbols().apply {
        months = application.resources.getStringArray(R.array.months)
    }

    @Provides
    @Singleton
    fun provideTimberDebugTree(): Timber.DebugTree = Timber.DebugTree()
}