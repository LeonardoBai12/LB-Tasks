package io.lb.lbtasks.task.domain.use_cases

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isNullOrEmpty
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.lb.lbtasks.task.data.repository.FakeTaskRepository
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.repository.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetTasksUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTasksUseCase

    @BeforeEach
    fun setUp() {
        repository = FakeTaskRepository()
        useCase = GetTasksUseCase(repository)
    }

    @Test
    fun `Getting tasks, returns in the correct order`() = runTest {
        repository.deleteTask(userData(), task())

        tasks().shuffled().forEach {
            repository.insertTask(userData(), it)
            advanceUntilIdle()
        }

        useCase.invoke(userData()).test {
            val emission1 = awaitItem()

            assertThat(emission1.data).isNull()
            assertThat(emission1.message).isNullOrEmpty()

            val emission2 = awaitItem()
            val tasks = emission2.data as List<Task>

            assertThat(tasks).isEqualTo(tasks())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
