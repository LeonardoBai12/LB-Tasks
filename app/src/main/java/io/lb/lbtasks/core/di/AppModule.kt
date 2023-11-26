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
import io.lb.lbtasks.core.util.LBTasksToaster
import io.lb.lbtasks.core.util.TASK
import io.lb.lbtasks.core.util.Toaster
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
    fun providesAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun providesGoogleAuthUiClient(
        app: Application,
        firebaseAuth: FirebaseAuth
    ): GoogleAuthClient {
        return GoogleAuthClientImpl(
            auth = firebaseAuth,
            context = app.applicationContext,
            oneTapClient = Identity.getSignInClient(app.applicationContext)
        )
    }

    @Provides
    @Singleton
    fun providesRealtimeDatabase(): FirebaseDatabase {
        return Firebase.database.apply {
            setPersistenceEnabled(true)
        }
    }

    @Provides
    @Singleton
    fun providesRealtimeDatabaseClient(
        database: FirebaseDatabase
    ): RealtimeDatabaseClient {
        return RealtimeDatabaseClientImpl(database.getReference(TASK))
    }

    @Provides
    @Singleton
    fun providesToaster(app: Application): Toaster {
        return LBTasksToaster(app.applicationContext)
    }
}
