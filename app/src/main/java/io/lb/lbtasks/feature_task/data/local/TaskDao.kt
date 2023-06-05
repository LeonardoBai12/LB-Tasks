package io.lb.lbtasks.feature_task.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.lb.lbtasks.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleTask(taskEntity: Task)

    @Update
    suspend fun updateTask(taskEntity: Task)

    @Delete
    suspend fun deleteTask(taskEntity: Task)

    @Query(
        """
            SELECT * 
            FROM tasks
            ORDER BY deadlineDate, deadlineTime
        """
    )
    fun searchTasks(): Flow<List<Task>>
}
