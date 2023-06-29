package io.lb.lbtasks.sign_in.di

import android.app.Application
import com.google.android.gms.auth.api.identity.Identity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthUiClient
import io.lb.lbtasks.sign_in.data.repository.SignInRepositoryImpl
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import io.lb.lbtasks.sign_in.domain.use_cases.GetSignedInUserUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.LoginWIthEmailAndPasswordUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.LogoutUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInUseCases
import io.lb.lbtasks.sign_in.domain.use_cases.SignInWithEmailAndPasswordUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInWithGoogleUseCase

@Module
@InstallIn(ViewModelComponent::class)
object SignInModule {
    @Provides
    fun providesSignInRepository(googleAuthUiClient: GoogleAuthUiClient): SignInRepository {
        return SignInRepositoryImpl(googleAuthUiClient)
    }

    @Provides
    fun providesSignInUseCases(repository: SignInRepository): SignInUseCases {
        return SignInUseCases(
            loginWithEmailAndPasswordUseCase = LoginWIthEmailAndPasswordUseCase(repository),
            signInWithEmailAndPasswordUseCase = SignInWithEmailAndPasswordUseCase(repository),
            signInWithGoogleUseCase = SignInWithGoogleUseCase(repository),
            signInUseCase = SignInUseCase(repository),
            getSignedInUserUseCase = GetSignedInUserUseCase(repository),
            logoutUseCase = LogoutUseCase(repository)
        )
    }

    @Provides
    fun providesGoogleAuthUiClient(app: Application): GoogleAuthUiClient {
        return GoogleAuthUiClient(
            context = app.applicationContext,
            oneTapClient = Identity.getSignInClient(app.applicationContext)
        )
    }
}
