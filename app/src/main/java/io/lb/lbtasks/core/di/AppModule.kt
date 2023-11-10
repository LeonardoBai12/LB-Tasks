package io.lb.lbtasks.core.di

import android.app.Application
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.lb.lbtasks.core.util.TASK
import io.lb.lbtasks.core.util.TASK_TEST
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClient
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClientImpl
import io.lb.lbtasks.task.data.remote.RealtimeDatabaseClient
import io.lb.lbtasks.task.data.remote.RealtimeDatabaseClientImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesGoogleAuthUiClient(app: Application): GoogleAuthClient {
        val firebaseAuth = Firebase.auth.apply {
            useEmulator("10.0.2.2", 9099)
        }

        val oneTapClient = Identity.getSignInClient(app.applicationContext)

        return GoogleAuthClientImpl(
            auth = firebaseAuth,
            context = app.applicationContext,
            oneTapClient = oneTapClient
        )
    }

    @Provides
    @Singleton
    fun providesRealtimeDatabase(): RealtimeDatabaseClient {
        val database = Firebase.database.apply {
            useEmulator("10.0.2.2", 9000)
            setPersistenceEnabled(true)
        }.getReference(TASK_TEST)

        return RealtimeDatabaseClientImpl(database)
    }
}
