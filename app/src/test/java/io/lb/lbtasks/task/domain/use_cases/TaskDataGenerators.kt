package io.lb.lbtasks.task.domain.use_cases

import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.model.TaskType

fun task(): Task {
    return Task(
        uuid = "TaskId",
        title = "Task Title",
        description = "Task description",
        taskType = TaskType.BUSINESS.name,
        deadlineDate = "2023-12-12",
        deadlineTime = "22:22",
    )
}

fun tasks(): List<Task> {
    return listOf(
        task().copy(uuid = "TaskId1", title = "Task title1"),
        task().copy(uuid = "TaskId2", title = "Task title2"),
        task().copy(uuid = "TaskId3", title = "Task title3"),
        task().copy(uuid = "TaskId4", title = "Task title4"),
        task().copy(uuid = "TaskId5", title = "Task title5"),
        task().copy(uuid = "TaskId6", title = "Task title6"),
    )
}
