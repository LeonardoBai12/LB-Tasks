package io.lb.lbtasks.task.data.repository

import io.lb.lbtasks.task.data.local.TaskDao
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {
    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    override fun getTasks() = dao.searchTasks()

    override suspend fun insertTask(task: Task) {
        dao.insertSingleTask(task)
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }
}
