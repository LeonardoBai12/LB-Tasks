package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository
import kotlin.jvm.Throws

class UpdateTaskUseCase(
    private val repository: TaskRepository
) {
    @Throws(Exception::class)
    suspend operator fun invoke(
        task: Task,
        title: String,
        description: String = "",
        deadlineDate: String,
        deadlineTime: String
    ) {
        if (task.title.isBlank())
            throw Exception("Atenção! Adicione um título à sua tarefa para continuar.")

        repository.updateTask(
            task.apply {
                this.title = title
                this.description = description
                this.deadlineDate = deadlineDate.replace("/", "-")
                this.deadlineTime = deadlineTime
            }
        )
    }
}
