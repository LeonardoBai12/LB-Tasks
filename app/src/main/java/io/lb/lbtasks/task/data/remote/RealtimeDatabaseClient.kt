package io.lb.lbtasks.task.data.remote

import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabaseClient {
    suspend fun insertTask(user: UserData, task: Task)
    suspend fun deleteTask(user: UserData, task: Task)
    fun getTasksFromUser(user: UserData): Flow<Resource<List<Task>>>
}
