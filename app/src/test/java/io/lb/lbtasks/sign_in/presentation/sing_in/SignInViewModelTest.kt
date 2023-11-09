package io.lb.lbtasks.sign_in.presentation.sing_in

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isNullOrEmpty
import assertk.assertions.isTrue
import io.lb.lbtasks.sign_in.data.repository.FakeGoogleAuthUiClient
import io.lb.lbtasks.sign_in.data.repository.FakeSignInRepository
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import io.lb.lbtasks.sign_in.domain.use_cases.GetSignedInUserUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.LoginWithEmailAndPasswordUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.LogoutUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInUseCases
import io.lb.lbtasks.sign_in.domain.use_cases.SignInWithEmailAndPasswordUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.SignInWithGoogleUseCase
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.lb.lbtasks.sign_in.util.MainCoroutineExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineExtension::class)
internal class SignInViewModelTest {
    private lateinit var repository: SignInRepository
    private lateinit var useCases: SignInUseCases
    private lateinit var viewModel: SignInViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeSignInRepository()
        useCases = SignInUseCases(
            loginWithEmailAndPasswordUseCase = LoginWithEmailAndPasswordUseCase(repository),
            signInWithEmailAndPasswordUseCase = SignInWithEmailAndPasswordUseCase(repository),
            signInWithGoogleUseCase = SignInWithGoogleUseCase(repository),
            signInUseCase = SignInUseCase(repository),
            getSignedInUserUseCase = GetSignedInUserUseCase(repository),
            logoutUseCase = LogoutUseCase(repository)
        )
        viewModel = SignInViewModel(useCases)
    }
    @Test
    fun `Getting signed in user, returns null`() = runTest {
        val result = viewModel.getSignedInUser()

        advanceUntilIdle()

        assertThat(result).isNull()
    }

    @Test
    fun `Getting signed in user after a login, returns the user`() = runTest {
        viewModel.loginWithEmailAndPassword(
            "fellow@user.com",
            "password"
        )

        advanceUntilIdle()

        val result = viewModel.getSignedInUser()

        advanceUntilIdle()

        assertThat(result).isEqualTo(
            userData()
        )
    }

    @Test
    fun `Logging out right after logging in, returns null`() = runTest {
        viewModel.loginWithEmailAndPassword(
            "fellow@user.com",
            "password"
        )

        advanceUntilIdle()

        viewModel.logout()
        advanceUntilIdle()

        val result = viewModel.getSignedInUser()

        advanceUntilIdle()

        assertThat(result).isNull()
    }

    @Test
    fun `Getting signed in user after a sign in, returns the user`() = runTest {
        viewModel.signInWithEmailAndPassword(
            "fellow@user.com",
            "password",
            "password"
        )

        advanceUntilIdle()

        val result = viewModel.getSignedInUser()

        advanceUntilIdle()

        assertThat(result).isEqualTo(
            userData().copy(
                userId = "randomNewUserId"
            )
        )
    }

    @Test
    fun `Signing in, state is success`() = runTest {
        viewModel.signInWithEmailAndPassword(
            "fellow@user.com",
            "password",
            "password"
        )

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isTrue()
        assertThat(viewModel.state.value.signInError).isNullOrEmpty()
    }

    @Test
    fun `Signing in with no password, state has error message`() = runTest {
        viewModel.signInWithEmailAndPassword(
            "fellow@user.com",
            "",
            "password"
        )

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isFalse()
        assertThat(viewModel.state.value.signInError).isEqualTo("Write your password")
    }

    @Test
    fun `Signing in with password not matching, state has error message`() = runTest {
        viewModel.signInWithEmailAndPassword(
            "fellow@user.com",
            "password",
            "wrongPassword"
        )

        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission)
                .isEqualTo(
                    SignInViewModel.UiEvent.ShowToast(
                        "The passwords don't match"
                    )
                )
        }

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isFalse()
        assertThat(viewModel.state.value.signInError).isEqualTo("The passwords don't match")
    }

    @Test
    fun `Signing in with no email, state has error message`() = runTest {
        viewModel.signInWithEmailAndPassword(
            "",
            "password",
            "password"
        )

        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(
                SignInViewModel.UiEvent.ShowToast(
                    "Write a valid email"
                )
            )
        }

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isFalse()
        assertThat(viewModel.state.value.signInError)
            .isEqualTo("Write a valid email")
    }

    @Test
    fun `Signing in with invalid email, state has error message`() = runTest {
        viewModel.signInWithEmailAndPassword(
            "fellowuser.com",
            "password",
            "password"
        )

        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(
                SignInViewModel.UiEvent.ShowToast(
                    "Write a valid email"
                )
            )
        }

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isFalse()
        assertThat(viewModel.state.value.signInError)
            .isEqualTo("Write a valid email")
    }

    @Test
    fun `Logging in, state is success`() = runTest {
        viewModel.loginWithEmailAndPassword(
            "fellow@user.com",
            "password",
        )

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isTrue()
        assertThat(viewModel.state.value.signInError).isNullOrEmpty()
    }

    @Test
    fun `Logging in with no password, state has error message`() = runTest {
        viewModel.loginWithEmailAndPassword(
            "fellow@user.com",
            "",
        )

        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission)
                .isEqualTo(
                    SignInViewModel.UiEvent.ShowToast(
                        "Write your password"
                    )
                )
        }

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isFalse()
        assertThat(viewModel.state.value.signInError)
            .isEqualTo("Write your password")
    }

    @Test
    fun `Logging in with no email, state has error message`() = runTest {
        viewModel.loginWithEmailAndPassword(
            "",
            "password",
        )

        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(
                SignInViewModel.UiEvent.ShowToast(
                    "Write a valid email"
                )
            )
        }

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isFalse()
        assertThat(viewModel.state.value.signInError)
            .isEqualTo("Write a valid email")
    }

    @Test
    fun `Logging in with invalid email, state has error message`() = runTest {
        viewModel.loginWithEmailAndPassword(
            "fellowuser.com",
            "password",
        )

        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(
                SignInViewModel.UiEvent.ShowToast(
                    "Write a valid email"
                )
            )
        }

        advanceUntilIdle()

        assertThat(viewModel.state.value.isSignInSuccessful).isFalse()
        assertThat(viewModel.state.value.signInError)
            .isEqualTo("Write a valid email")
    }

    @Test
    fun `Resetting state, makes the state brand new`() = runTest {
        viewModel.loginWithEmailAndPassword(
            "fellowuser.com",
            "password",
        )

        advanceUntilIdle()

        viewModel.resetState()

        assertThat(viewModel.state.value.isSignInSuccessful).isFalse()
        assertThat(viewModel.state.value.signInError).isNull()
    }
}
