package io.lb.lbtasks.sign_in.domain.use_cases

import assertk.assertThat
import assertk.assertions.isNull
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import kotlinx.coroutines.test.runTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LogoutUseCaseTest {
    private lateinit var repository: SignInRepository
    private lateinit var useCase: LogoutUseCase

    @BeforeEach
    fun setUp() {
        repository = FakeSignInRepository()
        useCase = LogoutUseCase(repository)
    }

    @Test
    fun `Logging out right after logging in, resets the user either way`() = runTest {
        repository.loginWithEmailAndPassword(
            "fellow@user.com",
            "Correct password"
        )

        useCase.invoke()
        val result = repository.getSignedInUser()

        assertThat(result).isNull()
    }
}
