package io.lb.lbtasks.sign_in.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClient
import io.lb.lbtasks.sign_in.data.repository.FakeGoogleAuthUiClient.Companion.OLD_USER_EMAIL
import io.lb.lbtasks.sign_in.data.repository.FakeGoogleAuthUiClient.Companion.OLD_USER_PASSWORD
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SignInRepositoryImplTest {
    private lateinit var authClient: GoogleAuthClient
    private lateinit var repository: SignInRepository

    @BeforeEach
    fun setUp() {
        authClient = FakeGoogleAuthUiClient()
        repository = SignInRepositoryImpl(authClient)
    }

    @Test
    fun `Getting signed in user, returns null`() {
        val result = repository.getSignedInUser()
        assertThat(result).isNull()
    }

    @Test
    fun `Getting user right after logging in, returns the user`() = runTest {
        repository.loginWithEmailAndPassword(
            OLD_USER_EMAIL,
            OLD_USER_PASSWORD
        )

        val result = repository.getSignedInUser()
        assertThat(result).isEqualTo(
            userData().copy(
                email = OLD_USER_EMAIL,
            )
        )
    }

    @Test
    fun `Logging in with correct data, returns the user`() = runTest {
        val result = repository.loginWithEmailAndPassword(
            OLD_USER_EMAIL,
            OLD_USER_PASSWORD
        )

        assertThat(result.data).isEqualTo(
            userData().copy(
                email = OLD_USER_EMAIL,
            )
        )
    }

    @Test
    fun `Logging in with incorrect password, returns null`() = runTest {
        val result = repository.loginWithEmailAndPassword(
            OLD_USER_EMAIL,
            "Incorrect password"
        )

        assertThat(result.data).isNull()
    }

    @Test
    fun `Logging in with not registered email, returns null`() = runTest {
        val result = repository.loginWithEmailAndPassword(
            "newUser@user.com",
            OLD_USER_PASSWORD
        )

        assertThat(result.data).isNull()
    }

    @Test
    fun `Signing in with registered email, returns the user`() = runTest {
        val result = repository.signInWithEmailAndPassword(
            "newUser@user.com",
            "newPassword"
        )

        assertThat(result.data).isEqualTo(
            userData().copy(
                userId = "randomNewUserId",
                email = "newUser@user.com",
            )
        )
    }

    @Test
    fun `Signing in with Google, returns the user`() = runTest {
        val result = repository.signInWithGoogle(mockk(relaxed = true))

        assertThat(result.data).isEqualTo(
            userData().copy(
                userId = "randomNewUserId",
                email = "someGoogleEmail@user.com",
            )
        )
    }

    @Test
    fun `Signing in with registered email, returns null`() = runTest {
        val result = repository.signInWithEmailAndPassword(
            OLD_USER_EMAIL,
            OLD_USER_PASSWORD
        )

        assertThat(result.data).isNull()
    }

    @Test
    fun `Logging out right after logging, returns null either way`() = runTest {
        repository.loginWithEmailAndPassword(
            OLD_USER_EMAIL,
            OLD_USER_PASSWORD
        )
        repository.logout()

        val result = repository.getSignedInUser()

        assertThat(result).isNull()
    }
}
