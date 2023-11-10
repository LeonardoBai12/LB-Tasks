package io.lb.lbtasks.task.presentation.details

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.hasToString
import assertk.assertions.isEqualTo
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.lb.lbtasks.task.domain.use_cases.TaskUseCases
import io.lb.lbtasks.task.domain.use_cases.task
import io.lb.lbtasks.task.presentation.fakeUseCases
import io.lb.lbtasks.util.MainCoroutineExtension
import io.mockk.coVerify
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineExtension::class)
internal class TaskDetailsViewModelTest {
    private lateinit var useCases: TaskUseCases
    private lateinit var viewModel: TaskDetailsViewModel
    private val userData = userData()

    @BeforeEach
    fun setUp() {
        useCases = fakeUseCases()
        viewModel = TaskDetailsViewModel(useCases)
        viewModel.userData = userData
    }

    @Test
    fun `Inserting a task, finishes screen`() = runTest {
        val task = task().copy(title = "New task title")
        viewModel.state.value.task = task

        viewModel.eventFlow.test {
            viewModel.onEvent(
                TaskDetailsEvent.RequestInsert(
                    title = task.title,
                    description = task.description!!,
                    date = task.deadlineDate!!,
                    time = task.deadlineTime!!,
                )
            )

            val emission = awaitItem()
            assertThat(emission).isEqualTo(TaskDetailsViewModel.UiEvent.Finish)
        }
    }

    @Test
    fun `Inserting a task without a title, shows toast`() = runTest {
        val task = task().copy(title = "")
        viewModel.state.value.task = task

        viewModel.eventFlow.test {
            viewModel.onEvent(
                TaskDetailsEvent.RequestInsert(
                    title = task.title,
                    description = task.description!!,
                    date = task.deadlineDate!!,
                    time = task.deadlineTime!!,
                )
            )

            val emission = awaitItem()
            assertThat(emission).isEqualTo(
                TaskDetailsViewModel.UiEvent.ShowToast(
                    "You can't save without a title"
                )
            )
        }
    }

    @Test
    fun `Inserting a task without a task type, shows toast`() = runTest {
        val task = task().copy(taskType = "")
        viewModel.state.value.task = task

        viewModel.eventFlow.test {
            viewModel.onEvent(
                TaskDetailsEvent.RequestInsert(
                    title = task.title,
                    description = task.description!!,
                    date = task.deadlineDate!!,
                    time = task.deadlineTime!!,
                )
            )

            val emission = awaitItem()
            assertThat(emission).isEqualTo(
                TaskDetailsViewModel.UiEvent.ShowToast(
                    "You can't save without a task type"
                )
            )
        }
    }

    @Test
    fun `Updating a task, finishes screen`() = runTest {
        val task = task().copy(title = "New task title")
        viewModel.state.value.task = task

        viewModel.eventFlow.test {
            viewModel.onEvent(
                TaskDetailsEvent.RequestUpdate(
                    title = task.title,
                    description = task.description!!,
                    date = task.deadlineDate!!,
                    time = task.deadlineTime!!,
                )
            )

            val emission = awaitItem()
            assertThat(emission).isEqualTo(TaskDetailsViewModel.UiEvent.Finish)
        }
    }

    @Test
    fun `Updating a task without a title, shows toast`() = runTest {
        val task = task().copy(title = "")
        viewModel.state.value.task = task

        viewModel.eventFlow.test {
            viewModel.onEvent(
                TaskDetailsEvent.RequestUpdate(
                    title = task.title,
                    description = task.description!!,
                    date = task.deadlineDate!!,
                    time = task.deadlineTime!!,
                )
            )

            val emission = awaitItem()
            assertThat(emission).isEqualTo(
                TaskDetailsViewModel.UiEvent.ShowToast(
                    "You can't save without a title"
                )
            )
        }
    }

    @Test
    fun `Updating a task without a task type, shows toast`() = runTest {
        val task = task().copy(taskType = "")
        viewModel.state.value.task = task

        viewModel.eventFlow.test {
            viewModel.onEvent(
                TaskDetailsEvent.RequestUpdate(
                    title = task.title,
                    description = task.description!!,
                    date = task.deadlineDate!!,
                    time = task.deadlineTime!!,
                )
            )

            val emission = awaitItem()
            assertThat(emission).isEqualTo(
                TaskDetailsViewModel.UiEvent.ShowToast(
                    "You can't save without a task type"
                )
            )
        }
    }
}