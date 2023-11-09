package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository

class InsertTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        userData: UserData,
        title: String,
        description: String = "",
        taskType: String,
        deadlineDate: String,
        deadlineTime: String
    ) {
        if (title.isBlank())
            throw Exception("You can't save without a title")

        if (taskType.isBlank())
            throw Exception("You can't save without a task type")

        repository.insertTask(
            userData = userData,
            task = Task(
                title = title,
                description = description,
                taskType = taskType,
                deadlineDate = deadlineDate.replace("/", "-"),
                deadlineTime = deadlineTime,
            )
        )
    }
}
