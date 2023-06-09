package io.lb.lbtasks.task.presentation.details

sealed class TaskDetailsEvent {
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
