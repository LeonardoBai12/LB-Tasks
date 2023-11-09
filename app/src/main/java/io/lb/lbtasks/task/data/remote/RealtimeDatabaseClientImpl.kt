package io.lb.lbtasks.task.data.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.core.util.TASK
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RealtimeDatabaseClientImpl(
    private val database: DatabaseReference
) : RealtimeDatabaseClient {
    override suspend fun insertTask(user: UserData, task: Task) {
        database.child(user.userId ?: return)
            .child(task.uuid)
            .setValue(task)
            .await()
    }

    override suspend fun deleteTask(user: UserData, task: Task) {
        database.child(user.userId ?: return)
            .child(task.uuid)
            .setValue(null)
            .await()
    }

    @Suppress("UNCHECKED_CAST")
    override fun getTasksFromUser(user: UserData) = flow {
        val result = database.child(user.userId ?: return@flow).get().await()
        val tasks = result.children.map {
            Task.fromSnapshot(it.value as HashMap<String, String>)
        }

        emit(
            Resource.Success(
                data = tasks.sortedWith(
                    compareBy(
                        Task::deadlineDate,
                        Task::deadlineTime,
                        Task::title,
                    )
                )
            )
        )

        emit(Resource.Loading(false))
    }
}
