package io.lb.lbtasks.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.lb.lbtasks.feature_task.data.local.TaskEntity

@Database(
    version = 1,
    entities = [
        TaskEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskEntity
}

