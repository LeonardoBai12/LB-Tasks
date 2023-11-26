package io.lb.lbtasks.task.di

import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn
import io.lb.lbtasks.task.data.remote.RealtimeDatabaseClient
import io.lb.lbtasks.task.data.repository.TaskRepositoryImpl
import io.lb.lbtasks.task.domain.repository.TaskRepository
import io.lb.lbtasks.task.domain.use_cases.DeleteTaskUseCase
import io.lb.lbtasks.task.domain.use_cases.GetTasksUseCase
import io.lb.lbtasks.task.domain.use_cases.InsertTaskUseCase
import io.lb.lbtasks.task.domain.use_cases.TaskUseCases
import io.lb.lbtasks.task.domain.use_cases.UpdateTaskUseCase

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [TaskModule::class]
)
object TestTaskModule {
    @Provides
    fun providesTaskRepository(
        realtimeDatabaseClient: RealtimeDatabaseClient,
    ): TaskRepository {
        return TaskRepositoryImpl(realtimeDatabaseClient)
    }

    @Provides
    fun providesTaskUseCases(repository: TaskRepository) =
        TaskUseCases(
            deleteTaskUseCase = DeleteTaskUseCase(repository),
            getTasksUseCase = GetTasksUseCase(repository),
            insertTaskUseCase = InsertTaskUseCase(repository),
            updateTaskUseCase = UpdateTaskUseCase(repository)
        )
}
