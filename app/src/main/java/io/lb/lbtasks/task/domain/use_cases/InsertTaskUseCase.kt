package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository

/**
 * Created a new task.
 */
class InsertTaskUseCase(
    private val repository: TaskRepository
) {
    /**
     * Created a new task.
     *
     * @param userData Data from the user to assign the task.
     * @param title Task title.
     * @param description Task description.
     * @param taskType Task type.
     * @param deadlineDate Deadline date.
     * @param deadlineTime Deadline time.
     */
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
