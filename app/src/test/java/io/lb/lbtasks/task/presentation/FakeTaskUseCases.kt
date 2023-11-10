package io.lb.lbtasks.task.presentation

import io.lb.lbtasks.task.data.repository.FakeTaskRepository
import io.lb.lbtasks.task.domain.use_cases.DeleteTaskUseCase
import io.lb.lbtasks.task.domain.use_cases.GetTasksUseCase
import io.lb.lbtasks.task.domain.use_cases.InsertTaskUseCase
import io.lb.lbtasks.task.domain.use_cases.TaskUseCases
import io.lb.lbtasks.task.domain.use_cases.UpdateTaskUseCase

fun fakeUseCases(): TaskUseCases {
    val repository = FakeTaskRepository()

    return TaskUseCases(
        deleteTaskUseCase = DeleteTaskUseCase(repository),
        getTasksUseCase = GetTasksUseCase(repository),
        insertTaskUseCase = InsertTaskUseCase(repository),
        updateTaskUseCase = UpdateTaskUseCase(repository),
    )
}
