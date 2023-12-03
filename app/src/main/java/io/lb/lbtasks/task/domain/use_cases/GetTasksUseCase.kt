package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

/**
 * Gets all tasks from an specific user.
 */
class GetTasksUseCase(
    private val repository: TaskRepository
) {
    /**
     * Gets all tasks from an specific user.
     *
     * @return A coroutine flow with all task from the provided user.
     */
    operator fun invoke(userData: UserData): Flow<Resource<List<Task>>> {
        return repository.getTasks(userData)
    }
}
