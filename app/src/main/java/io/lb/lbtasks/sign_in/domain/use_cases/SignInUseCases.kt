package io.lb.lbtasks.sign_in.domain.use_cases

data class SignInUseCases(
    val loginWithEmailAndPasswordUseCase: LoginWIthEmailAndPasswordUseCase,
    val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    val signInUseCase: SignInUseCase,
    val getSignedInUserUseCase: GetSignedInUserUseCase,
    val logoutUseCase: LogoutUseCase,
)
