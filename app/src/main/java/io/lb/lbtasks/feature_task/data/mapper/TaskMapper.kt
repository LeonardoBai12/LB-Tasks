package io.lb.lbtasks.feature_task.data.mapper

import io.lb.lbtasks.feature_task.data.local.TaskEntity
import io.lb.lbtasks.feature_task.domain.model.Task

fun Task.toTaskEntity() =
    TaskEntity(
        title = title,
        description = description,
        taskType = taskType,
        deadlineDate = deadlineDate,
    )

fun TaskEntity.toTask() =
    Task(
        title = title,
        description = description,
        taskType = taskType,
        deadlineDate = deadlineDate,
    )
