package io.lb.lbtasks.task.presentation.details

import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.lb.lbtasks.task.domain.use_cases.TaskUseCases
import io.lb.lbtasks.task.presentation.fakeUseCases
import io.lb.lbtasks.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineExtension::class)
internal class TaskDetailsViewModelTest {
    private lateinit var useCases: TaskUseCases
    private lateinit var viewModel: TaskDetailsViewModel

    @BeforeEach
    fun setUp() {
        useCases = fakeUseCases()
        viewModel = TaskDetailsViewModel(useCases)
        viewModel.userData = userData()
    }


}