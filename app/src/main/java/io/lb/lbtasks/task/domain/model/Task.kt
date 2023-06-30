package io.lb.lbtasks.task.domain.model

import com.google.gson.Gson
import java.util.UUID

data class Task(
    val uuid: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String? = null,
    val taskType: String,
    var deadlineDate: String? = null,
    var deadlineTime: String? = null,
) {
    companion object {
        fun fromJson(json: String): Task = Gson().fromJson(json, Task::class.java)

        fun fromSnapshot(hashMap: HashMap<String, String>): Task {
            return Task(
                uuid = hashMap["uuid"] ?: "",
                title = hashMap["title"] ?: "",
                description = hashMap["description"],
                taskType = hashMap["taskType"] ?: "",
                deadlineDate = hashMap["deadlineDate"],
                deadlineTime = hashMap["deadlineTime"]
            )
        }
    }

    fun toJson() = Gson().toJson(this).toString()
}
