package io.lb.lbtasks.task.data.repository

import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.data.remote.RealtimeDatabaseClientImpl
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val realtimeDatabaseClient: RealtimeDatabaseClientImpl,
) : TaskRepository {
    override suspend fun deleteTask(userData: UserData, task: Task) {
        realtimeDatabaseClient.deleteTask(userData, task)
    }

    override fun getTasks(userData: UserData) = realtimeDatabaseClient.getTasksFromUser(userData)

    override suspend fun insertTask(userData: UserData, task: Task) {
        realtimeDatabaseClient.insertTask(userData, task)
    }
}
