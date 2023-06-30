package io.lb.lbtasks.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.lb.lbtasks.task.data.remote.RealtimeDatabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesRealtimeDatabase(): RealtimeDatabaseClient {
        return RealtimeDatabaseClient()
    }
}
