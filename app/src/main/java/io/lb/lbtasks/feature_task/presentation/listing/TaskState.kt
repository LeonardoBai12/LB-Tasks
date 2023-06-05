package io.lb.lbtasks.feature_task.presentation.listing

import io.lb.lbtasks.feature_task.domain.model.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
)
