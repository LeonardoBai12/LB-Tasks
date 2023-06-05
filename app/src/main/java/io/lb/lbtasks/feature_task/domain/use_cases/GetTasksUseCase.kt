package io.lb.lbtasks.feature_task.domain.use_cases

import io.lb.lbtasks.feature_task.domain.repository.TaskRepository

class GetTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke() = repository.getTasks()
}
