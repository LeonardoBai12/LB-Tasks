package io.lb.lbtasks.feature_task.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.lbtasks.core.data.local.AppDatabase
import io.lb.lbtasks.feature_task.data.local.TaskDao
import io.lb.lbtasks.feature_task.data.repository.TaskRepositoryImpl
import io.lb.lbtasks.feature_task.domain.repository.TaskRepository
import io.lb.lbtasks.feature_task.domain.use_cases.DeleteTaskUseCase
import io.lb.lbtasks.feature_task.domain.use_cases.GetTasksUseCase
import io.lb.lbtasks.feature_task.domain.use_cases.InsertTaskUseCase
import io.lb.lbtasks.feature_task.domain.use_cases.TaskUseCases
import io.lb.lbtasks.feature_task.domain.use_cases.UpdateTaskUseCase

@Module
@InstallIn(ViewModelComponent::class)
object TaskModule {
    @Provides
    fun providesCategoryDao(appDatabase: AppDatabase) = appDatabase.taskDao

    @Provides
    fun providesCategoryRepository(dao: TaskDao) = TaskRepositoryImpl(dao)

    @Provides
    fun providesCategoryUseCases(repository: TaskRepository) =
        TaskUseCases(
            deleteTaskUseCase = DeleteTaskUseCase(repository),
            getTasksUseCase = GetTasksUseCase(repository),
            insertTaskUseCase = InsertTaskUseCase(repository),
            updateTaskUseCase = UpdateTaskUseCase(repository),
        )
}
