package io.lb.lbtasks.task.domain.use_cases

import app.cash.turbine.test
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isNullOrEmpty
import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.lb.lbtasks.task.data.repository.FakeTaskRepository
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository
import io.lb.lbtasks.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineExtension::class)
internal class UpdateTaskUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: UpdateTaskUseCase

    @BeforeEach
    fun setUp() {
        repository = FakeTaskRepository()
        useCase = UpdateTaskUseCase(repository)
    }

    @Test
    fun `Updating a single task, task has new values`() = runTest {
        val task = task()
        useCase.invoke(
            userData = userData(),
            task = task,
            title = "New Title",
            description = "",
            taskType = task.taskType,
            deadlineDate = "2024-01-01",
            deadlineTime = "23:23"
        )
        advanceUntilIdle()

        repository.getTasks(userData()).test {
            val emission1 = awaitItem()

            assertThat(emission1.data).isNull()
            assertThat(emission1.message).isNullOrEmpty()

            val emission2 = awaitItem()

            assertThat(emission2.data?.first()).isEqualTo(
                Task(
                    uuid = task.uuid,
                    title = "New Title",
                    description = "",
                    taskType = task.taskType,
                    deadlineDate = "2024-01-01",
                    deadlineTime = "23:23"
                )
            )
            assertThat(emission2.message).isNullOrEmpty()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Updating a single task without a title, throws exception`() = runTest {
        assertFailure {
            val task = task()
            useCase.invoke(
                userData = userData(),
                task = task,
                title = "",
                description = "New Description",
                taskType = task.taskType,
                deadlineDate = "2024-01-01",
                deadlineTime = "23:23"
            )
        }
    }
}
