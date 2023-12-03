package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository

/**
 * Deletes an existent task.
 */
class DeleteTaskUseCase(
    private val repository: TaskRepository
) {
    /**
     * Deletes an existent task.
     */
    suspend operator fun invoke(userData: UserData, task: Task) {
        repository.deleteTask(userData, task)
    }
}
