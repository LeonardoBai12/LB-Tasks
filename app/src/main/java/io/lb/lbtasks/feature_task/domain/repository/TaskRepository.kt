package io.lb.lbtasks.feature_task.domain.repository

import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun deleteTask(task: Task)
    suspend fun getTasks(): Flow<Resource<List<Task>>>
    suspend fun insertTask(
        title: String,
        description: String = "",
        taskType: String,
        deadlineDate: String,
        deadlineTime: String
    )
    suspend fun updateTask(task: Task)
}
