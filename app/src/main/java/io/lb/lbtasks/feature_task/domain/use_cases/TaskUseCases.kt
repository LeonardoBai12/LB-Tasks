package io.lb.lbtasks.feature_task.domain.use_cases

data class TaskUseCases(
    val deleteTaskUseCase: DeleteTaskUseCase,
    val getTasksUseCase: GetTasksUseCase,
    val insertTaskUseCase: InsertTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase
)
