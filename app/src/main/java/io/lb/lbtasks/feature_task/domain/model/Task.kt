package io.lb.lbtasks.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String,
    var description: String? = null,
    val taskType: String,
    var deadlineDate: String? = null,
    var deadlineTime: String? = null,
) {
    companion object {
        fun fromJson(json: String): Task = Gson().fromJson(json, Task::class.java)
    }

    fun toJson() = Gson().toJson(this).toString()
}
