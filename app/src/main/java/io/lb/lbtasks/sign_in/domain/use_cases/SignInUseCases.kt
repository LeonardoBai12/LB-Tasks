package io.lb.lbtasks.sign_in.domain.use_cases

/**
 * All use cases of the sign/login features.
 */
data class SignInUseCases(
    val loginWithEmailAndPasswordUseCase: LoginWithEmailAndPasswordUseCase,
    val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    val signInUseCase: SignInUseCase,
    val getSignedInUserUseCase: GetSignedInUserUseCase,
    val logoutUseCase: LogoutUseCase,
)
