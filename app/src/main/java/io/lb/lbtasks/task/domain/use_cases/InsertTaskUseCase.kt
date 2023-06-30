package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository

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
            throw Exception("You can't save without a title")

        repository.insertTask(
            Task(
                title = title,
                description = description,
                taskType = taskType,
                deadlineDate = deadlineDate.replace("/", "-"),
                deadlineTime = deadlineTime,
            )
        )
    }
}
