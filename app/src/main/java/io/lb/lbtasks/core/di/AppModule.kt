package io.lb.lbtasks.core.di

import android.app.Application
import androidx.room.Room
import com.google.android.gms.auth.api.identity.Identity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.lb.lbtasks.core.data.local.AppDatabase
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthUiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "lbtasks.db"
        ).build()
    }
}
