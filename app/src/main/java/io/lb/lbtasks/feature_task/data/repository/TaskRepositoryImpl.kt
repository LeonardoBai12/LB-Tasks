package io.lb.lbtasks.feature_task.data.repository

import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.feature_task.data.local.TaskDao
import io.lb.lbtasks.feature_task.data.mapper.toTask
import io.lb.lbtasks.feature_task.data.mapper.toTaskEntity
import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {
    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.toTaskEntity())
    }

    override suspend fun getTasks(): Flow<Resource<List<Task>>> {
        return flow {
            emit(Resource.Loading(true))

            val localTasks = dao.searchTasks()

            localTasks.takeIf {
                it.isNotEmpty()
            }?.let { tasks ->
                emit(
                    Resource.Success(
                        data = tasks.map { it.toTask() }
                    )
                )

                emit(Resource.Loading(false))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun insertTask(
        title: String,
        description: String,
        taskType: String,
        deadlineDate: String,
        deadlineTime: String
    ) {
        dao.insertSingleTask(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task.toTaskEntity())
    }
}
