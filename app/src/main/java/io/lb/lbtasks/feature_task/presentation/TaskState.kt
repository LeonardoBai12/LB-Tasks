package io.lb.lbtasks.feature_task.presentation

import io.lb.lbtasks.feature_task.domain.model.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val loading: Boolean = true
)
