package io.lb.lbtasks.task.domain.use_cases

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isNullOrEmpty
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.lb.lbtasks.task.data.repository.FakeTaskRepository
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DeleteTaskUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: DeleteTaskUseCase

    @BeforeEach
    fun setUp() {
        repository = FakeTaskRepository()
        useCase = DeleteTaskUseCase(repository)
    }

    @Test
    fun `Deleting a single task, list will be empty`() = runTest {
        val task = task()

        useCase.invoke(
            userData = userData(),
            task = task
        )

        repository.getTasks(userData()).test {
            val emission1 = awaitItem()

            assertThat(emission1.data).isNull()
            assertThat(emission1.message).isNullOrEmpty()

            val emission2 = awaitItem()
            val tasks = emission2.data as List<Task>

            assertThat(tasks).isNotNull()
            assertThat(tasks).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
