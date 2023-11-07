package io.lb.lbtasks.sign_in.domain.use_cases

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SignInWithGoogleUseCaseTest {
    private lateinit var repository: SignInRepository
    private lateinit var useCase: SignInWithGoogleUseCase

    @BeforeEach
    fun setUp() {
        repository = FakeSignInRepository()
        useCase = SignInWithGoogleUseCase(repository)
    }

    @Test
    fun `Signing in with Google, returns the Google user`() = runTest {
        val result = useCase.invoke(mockk(relaxed = true))
        assertThat(result.data).isEqualTo(
            UserData(
                userId = "randomGoogleUserId",
                userName = "Fellow User",
                email = "googlefellow@user.com",
                profilePictureUrl = null
            )
        )
    }
}
