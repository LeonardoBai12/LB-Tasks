package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.task.domain.repository.TaskRepository

class GetTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke() = repository.getTasks()
}
