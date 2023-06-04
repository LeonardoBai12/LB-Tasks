package io.lb.lbtasks.feature_task.domain.use_cases

import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.domain.repository.TaskRepository

class InsertTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String = "",
        taskType: String,
        deadlineDate: String,
        deadlineTime: String
    ) {
        if (title.isBlank())
            throw Exception("Atenção! Adicione um título à sua tarefa para continuar.")

        repository.insertTask(
            Task(
                title = title,
                description = description,
                taskType = taskType,
                deadlineDate = deadlineDate,
                deadlineTime = deadlineTime,
            )
        )
    }
}
