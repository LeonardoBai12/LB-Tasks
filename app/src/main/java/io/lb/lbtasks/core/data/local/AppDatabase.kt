package io.lb.lbtasks.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.lb.lbtasks.task.data.local.TaskDao
import io.lb.lbtasks.task.domain.model.Task

@Database(
    version = 1,
    entities = [
        Task::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}
