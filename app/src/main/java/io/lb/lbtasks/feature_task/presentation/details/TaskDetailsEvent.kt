package io.lb.lbtasks.feature_task.presentation.details

sealed class TaskDetailsEvent {
    object RequestDelete : TaskDetailsEvent()

    data class RequestInsert(
        val title: String,
        val description: String,
        val date: String,
        val time: String,
    ) : TaskDetailsEvent()

    data class RequestUpdate(
        val title: String,
        val description: String,
        val date: String,
        val time: String,
    ) : TaskDetailsEvent()
}
