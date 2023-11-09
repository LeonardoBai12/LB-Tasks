package io.lb.lbtasks.task.presentation.listing

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class TaskViewModelTest {
    @Test
    fun `When I test a flow, I will do something like this`() = runTest {
        val viewModel = mockk<TaskViewModel>()

        viewModel.state.test {
            val stateEmission1 = awaitItem()
            assertThat(stateEmission1.loading).isFalse()
            assertThat(stateEmission1.tasks).isEmpty()

            viewModel.getTasks(userData = mockk())

            val stateEmission2 = awaitItem()
            assertThat(stateEmission2.loading).isTrue()
            assertThat(stateEmission2.tasks).isEmpty()
        }
    }
}
