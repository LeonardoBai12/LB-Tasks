package io.lb.lbtasks.task.presentation.listing

import io.lb.lbtasks.task.domain.use_cases.TaskUseCases
import io.lb.lbtasks.task.presentation.details.TaskDetailsViewModel
import io.lb.lbtasks.task.presentation.fakeUseCases
import io.lb.lbtasks.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
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
    }

    // @Test
    // fun `When I test a flow, I will do something like this`() = runTest {
    //     val viewModel = mockk<TaskViewModel>()

    //     viewModel.state.test {
    //         val stateEmission1 = awaitItem()
    //         assertThat(stateEmission1.loading).isFalse()
    //         assertThat(stateEmission1.tasks).isEmpty()

    //         viewModel.getTasks(userData = mockk())

    //         val stateEmission2 = awaitItem()
    //         assertThat(stateEmission2.loading).isTrue()
    //         assertThat(stateEmission2.tasks).isEmpty()
    //     }
    // }
}
