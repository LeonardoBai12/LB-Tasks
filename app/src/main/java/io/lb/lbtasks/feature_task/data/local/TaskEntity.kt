package io.lb.lbtasks.feature_task.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.lb.lbtasks.feature_task.domain.model.TaskType
import java.util.Date
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: UUID? = null,
    val title: String,
    val description: String? = null,
    val taskType: String,
    val deadlineDate: Date? = null,
)
