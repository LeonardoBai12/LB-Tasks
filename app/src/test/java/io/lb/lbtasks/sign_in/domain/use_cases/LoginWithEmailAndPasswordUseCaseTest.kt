package io.lb.lbtasks.sign_in.domain.use_cases

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LoginWithEmailAndPasswordUseCaseTest {
    private lateinit var repository: SignInRepository
    private lateinit var useCase: LoginWithEmailAndPasswordUseCase

    @BeforeEach
    fun setUp() {
        repository = FakeSignInRepository()
        useCase = LoginWithEmailAndPasswordUseCase(repository)
    }

    @Test
    fun `Logging in with correct data, returns the actual user`() = runTest {
        val result = useCase.invoke(
            email = "fellow@user.com",
            password = "Correct",
        )

        assertThat(result.data).isEqualTo(userData())
    }

    @Test
    fun `Logging in with no email, throws exception`() = runTest {
        assertFailure {
            useCase.invoke(
                email = "",
                password = "Correct",
            )
        }
    }

    @Test
    fun `Logging in with no password, throws exception`() = runTest {
        assertFailure {
            useCase.invoke(
                email = "fellowuser.com",
                password = "Correct",
            )
        }
    }

    @Test
    fun `Logging in with misspelled email, throws exception`() = runTest {
        assertFailure {
            useCase.invoke(
                email = "fellowuser.com",
                password = "Correct",
            )
        }
    }
}
