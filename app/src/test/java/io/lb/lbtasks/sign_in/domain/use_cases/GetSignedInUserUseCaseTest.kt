package io.lb.lbtasks.sign_in.domain.use_cases

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import io.lb.lbtasks.sign_in.data.repository.FakeSignInRepository
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetSignedInUserUseCaseTest {
    private lateinit var repository: SignInRepository
    private lateinit var useCase: GetSignedInUserUseCase

    @BeforeEach
    fun setUp() {
        repository = FakeSignInRepository()
        useCase = GetSignedInUserUseCase(repository)
    }

    @Test
    fun `Getting user without logging in, returns null`() {
        val result = useCase.invoke()
        assertThat(result).isNull()
    }

    @Test
    fun `Getting user right after logging in, returns the user`() = runTest {
        repository.loginWithEmailAndPassword(
            "fellow@user.com",
            "Correct password"
        )

        val result = useCase.invoke()

        assertThat(result).isEqualTo(userData())
    }
}
