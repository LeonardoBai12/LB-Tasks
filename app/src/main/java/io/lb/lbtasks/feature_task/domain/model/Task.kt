package io.lb.lbtasks.feature_task.domain.model

import java.util.Date

data class Task(
    val title: String,
    val description: String? = null,
    val taskType: String,
    val deadlineDate: Date? = null,
)
