package io.lb.lbtasks.task.domain.use_cases

import app.cash.turbine.test
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isNullOrEmpty
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.lb.lbtasks.task.data.repository.FakeTaskRepository
import io.lb.lbtasks.task.domain.model.TaskType
import io.lb.lbtasks.task.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class InsertTaskUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: InsertTaskUseCase

    @BeforeEach
    fun setUp() {
        repository = FakeTaskRepository()
        useCase = InsertTaskUseCase(repository)
    }

    @Test
    fun `Inserting a single task, task has new values`() = runTest {
        useCase.invoke(
            userData = userData(),
            title = "New Title",
            description = "",
            taskType = TaskType.HOBBIES.name,
            deadlineDate = "2024-01-01",
            deadlineTime = "23:23"
        )

        repository.getTasks(userData()).test {
            val emission1 = awaitItem()

            assertThat(emission1.data).isNull()
            assertThat(emission1.message).isNullOrEmpty()

            val emission2 = awaitItem()
            val newTask = emission2.data?.last()

            assertThat(newTask?.title).isEqualTo("New Title")
            assertThat(newTask?.description).isEqualTo("")
            assertThat(newTask?.taskType).isEqualTo(TaskType.HOBBIES.name,)
            assertThat(newTask?.deadlineDate).isEqualTo("2024-01-01")
            assertThat(newTask?.deadlineTime).isEqualTo("23:23")
            assertThat(emission2.message).isNullOrEmpty()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Inserting a single task without a title, throws exception`() = runTest {
        assertFailure {
            useCase.invoke(
                userData = userData(),
                title = "",
                description = "New Description",
                taskType = TaskType.HOBBIES.name,
                deadlineDate = "2024-01-01",
                deadlineTime = "23:23"
            )
        }
    }

    @Test
    fun `Inserting a single task without a task type, throws exception`() = runTest {
        assertFailure {
            useCase.invoke(
                userData = userData(),
                title = "Title",
                description = "New Description",
                taskType = "",
                deadlineDate = "2024-01-01",
                deadlineTime = "23:23"
            )
        }
    }
}
