package io.lb.lbtasks.task.presentation.listing

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotEqualTo
import assertk.assertions.isTrue
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.lb.lbtasks.task.domain.use_cases.TaskUseCases
import io.lb.lbtasks.task.domain.use_cases.task
import io.lb.lbtasks.task.domain.use_cases.tasks
import io.lb.lbtasks.task.presentation.fakeUseCases
import io.lb.lbtasks.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineExtension::class)
internal class TaskViewModelTest {
    private lateinit var useCases: TaskUseCases
    private lateinit var viewModel: TaskViewModel

    @BeforeEach
    fun setUp() {
        useCases = fakeUseCases()
        viewModel = TaskViewModel(useCases)
        viewModel.userData = userData()
    }

    @Test
    fun `Restoring a task after deleting it, returns full list`() = runTest {
        val task = task()

        viewModel.state.test {
            viewModel.onEvent(TaskEvent.RequestDelete(task))
            viewModel.onEvent(TaskEvent.RestoreTask)

            val emission1 = awaitItem()
            assertThat(emission1.tasks).isEmpty()
            assertThat(emission1.loading).isTrue()

            val emission2 = awaitItem()
            assertThat(emission2.tasks).hasSize(1)
            with(emission2.tasks.first()) {
                assertThat(uuid).isNotEqualTo(task.uuid)
                assertThat(title).isEqualTo(task.title)
                assertThat(description).isEqualTo(task.description)
                assertThat(deadlineDate).isEqualTo(task.deadlineDate)
                assertThat(deadlineTime).isEqualTo(task.deadlineTime)
                assertThat(deadlineDate).isEqualTo(task.deadlineDate)
            }
            assertThat(emission2.loading).isTrue()

            val emission3 = awaitItem()
            assertThat(emission2.tasks).hasSize(1)
            assertThat(emission3.loading).isFalse()
        }
    }

    @Test
    fun `Deleting a task, returns empty list`() = runTest {
        val task = task()

        viewModel.state.test {
            viewModel.onEvent(TaskEvent.RequestDelete(task))

            val emission1 = awaitItem()
            assertThat(emission1.tasks).isEmpty()
            assertThat(emission1.loading).isTrue()

            val emission2 = awaitItem()
            assertThat(emission2.tasks).isEmpty()
            assertThat(emission2.loading).isFalse()
        }
    }

    @Test
    fun `Searching for an specific task, returns a list with it`() = runTest {
        val tasks = tasks().shuffled()
        tasks.forEach {
            useCases.insertTaskUseCase(
                userData = viewModel.userData!!,
                title = it.title,
                description = it.description!!,
                taskType = it.taskType,
                deadlineDate = it.deadlineDate!!,
                deadlineTime = it.deadlineTime!!,
            )
        }
        viewModel.getTasks(viewModel.userData!!)
        advanceUntilIdle()

        viewModel.state.test {
            viewModel.onEvent(TaskEvent.SearchedForTask("Task title1"))

            val emission1 = awaitItem()
            assertThat(emission1.tasks).hasSize(7)
            assertThat(emission1.loading).isFalse()

            val emission2 = awaitItem()
            assertThat(emission2.tasks).hasSize(1)
            with(emission2.tasks.first()) {
                val task = tasks().first()

                assertThat(uuid).isNotEqualTo(task.uuid)
                assertThat(title).isEqualTo(task.title)
                assertThat(description).isEqualTo(task.description)
                assertThat(deadlineDate).isEqualTo(task.deadlineDate)
                assertThat(deadlineTime).isEqualTo(task.deadlineTime)
                assertThat(deadlineDate).isEqualTo(task.deadlineDate)
            }
            assertThat(emission2.loading).isFalse()
        }
    }

    @Test
    fun `Searching with no filter, returns full list`() = runTest {
        val tasks = tasks().shuffled()
        tasks.forEach {
            useCases.insertTaskUseCase(
                userData = viewModel.userData!!,
                title = it.title,
                description = it.description!!,
                taskType = it.taskType,
                deadlineDate = it.deadlineDate!!,
                deadlineTime = it.deadlineTime!!,
            )
        }
        viewModel.getTasks(viewModel.userData!!)
        advanceUntilIdle()

        viewModel.state.test {
            viewModel.onEvent(TaskEvent.SearchedForTask(""))

            val emission = awaitItem()
            assertThat(emission.tasks).hasSize(7)
            assertThat(emission.loading).isFalse()
        }
    }

    @Test
    fun `Clearing state, makes a brand new state`() = runTest {
        viewModel.state.test {
            viewModel.clearState()

            val emission = awaitItem()
            assertThat(emission.tasks).isEmpty()
            assertThat(emission.loading).isTrue()
        }
    }
}
