package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository

/**
 * Updates an existent task.
 */
class UpdateTaskUseCase(
    private val repository: TaskRepository
) {
    /**
     * Updates an existent task.
     *
     * @param userData Data from the user to assign the task.
     * @param task The old task to be updated.
     * @param title New task title.
     * @param description New task description.
     * @param taskType New task type.
     * @param deadlineDate New deadline date.
     * @param deadlineTime New deadline time.
     */
    suspend operator fun invoke(
        userData: UserData,
        task: Task,
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
            task = task.copy(
                title = title,
                description = description,
                taskType = taskType,
                deadlineDate = deadlineDate.replace("/", "-"),
                deadlineTime = deadlineTime,
            )
        )
    }
}
