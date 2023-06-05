package io.lb.lbtasks.feature_task.domain.repository

import io.lb.lbtasks.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun deleteTask(task: Task)
    fun getTasks(): Flow<List<Task>>
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
}
