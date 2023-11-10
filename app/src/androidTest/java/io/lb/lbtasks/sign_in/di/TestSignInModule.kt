package io.lb.lbtasks.sign_in.di

import android.app.Application
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClient
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClientImpl
import io.lb.lbtasks.sign_in.data.repository.SignInRepositoryImpl
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import io.lb.lbtasks.sign_in.domain.use_cases.GetSignedInUserUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.LoginWithEmailAndPasswordUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.LogoutUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInUseCases
import io.lb.lbtasks.sign_in.domain.use_cases.SignInWithEmailAndPasswordUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInWithGoogleUseCase
import io.lb.lbtasks.task.di.TaskModule

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [SignInModule::class]
)
object TestSignInModule {
    @Provides
    fun providesSignInRepository(googleAuthUiClient: GoogleAuthClient): SignInRepository {
        return SignInRepositoryImpl(googleAuthUiClient)
    }

    @Provides
    fun providesSignInUseCases(repository: SignInRepository): SignInUseCases {
        return SignInUseCases(
            loginWithEmailAndPasswordUseCase = LoginWithEmailAndPasswordUseCase(repository),
            signInWithEmailAndPasswordUseCase = SignInWithEmailAndPasswordUseCase(repository),
            signInWithGoogleUseCase = SignInWithGoogleUseCase(repository),
            signInUseCase = SignInUseCase(repository),
            getSignedInUserUseCase = GetSignedInUserUseCase(repository),
            logoutUseCase = LogoutUseCase(repository)
        )
    }
}
