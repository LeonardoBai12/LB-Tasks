package io.lb.lbtasks.core.di

import android.app.Application
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClient
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClientImpl
import io.lb.lbtasks.task.data.remote.RealtimeDatabaseClient
import io.lb.lbtasks.task.data.remote.RealtimeDatabaseClientImpl
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {
    @Provides
    @Singleton
    fun providesGoogleAuthUiClient(app: Application): GoogleAuthClient {
        return GoogleAuthClientImpl(
            auth = mockk(relaxed = true),
            context = app.applicationContext,
            oneTapClient = mockk(relaxed = true)
        )
    }

    @Provides
    @Singleton
    fun providesRealtimeDatabase(): RealtimeDatabaseClient {
        return RealtimeDatabaseClientImpl(mockk(relaxed = true))
    }
}
