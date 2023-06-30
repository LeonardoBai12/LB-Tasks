package io.lb.lbtasks.task.presentation.listing

import io.lb.lbtasks.task.domain.model.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val loading: Boolean = true
)
