package io.lb.lbtasks.feature_task.presentation.listing

import io.lb.lbtasks.feature_task.domain.model.Task

sealed class TaskEvent {
    data class SearchedForTask(val filter: String) : TaskEvent()
    data class RequestDelete(val task: Task) : TaskEvent()
    object RestoreTask : TaskEvent()
}
