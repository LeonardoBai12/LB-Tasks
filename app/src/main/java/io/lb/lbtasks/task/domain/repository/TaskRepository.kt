package io.lb.lbtasks.task.domain.repository

import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun deleteTask(userData: UserData, task: Task)
    fun getTasks(userData: UserData): Flow<Resource<List<Task>>>
    suspend fun insertTask(userData: UserData, task: Task)
}
