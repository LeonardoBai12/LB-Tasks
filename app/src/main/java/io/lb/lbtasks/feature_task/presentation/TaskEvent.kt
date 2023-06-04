package io.lb.lbtasks.feature_task.presentation

import io.lb.lbtasks.feature_task.domain.model.Task

sealed class TaskEvent {
    data class SearchedForMeal(val filter: String) : TaskEvent()
    data class RequestDelete(val task: Task) : TaskEvent()
    data class RequestInsert(
        val title: String,
        val description: String = "",
        val taskType: String,
        val deadlineDate: String,
        val deadlineTime: String,
    ) : TaskEvent()
    data class RequestUpdate(val task: Task) : TaskEvent()
}
