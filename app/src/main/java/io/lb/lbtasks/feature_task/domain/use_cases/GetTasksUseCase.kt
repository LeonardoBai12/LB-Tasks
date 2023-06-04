package io.lb.lbtasks.feature_task.domain.use_cases

import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.domain.repository.TaskRepository

class GetTasksUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() = repository.getTasks()
}
