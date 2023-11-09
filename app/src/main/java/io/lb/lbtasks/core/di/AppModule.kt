package io.lb.lbtasks.core.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.lb.lbtasks.core.util.TASK
import io.lb.lbtasks.task.data.remote.RealtimeDatabaseClientImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesRealtimeDatabase(): RealtimeDatabaseClientImpl {
        var database: DatabaseReference

        Firebase.database.run {
            setPersistenceEnabled(true)
            database = getReference(TASK)
        }

        return RealtimeDatabaseClientImpl(database)
    }
}
