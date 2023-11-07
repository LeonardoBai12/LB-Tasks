package io.lb.lbtasks.sign_in.data.auth_client

import android.content.Context
import android.content.Intent
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GoogleAuthClientImplTest {
    private lateinit var auth: FirebaseAuth
    private lateinit var context: Context
    private lateinit var oneTapClient: SignInClient
    private lateinit var googleAuthClient: GoogleAuthClientImpl

    @BeforeEach
    fun setUp() {
        auth = mockk()
        context = mockk()
        oneTapClient = mockk()

        googleAuthClient = GoogleAuthClientImpl(auth, context, oneTapClient)
    }

    @Test
    fun `Signing in with email and password, signs in successfully`() = runTest {
        val email = "fellow@user.com"
        val password = "password"
        val task: Task<AuthResult> = spyk<Task<AuthResult>>().apply {
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns spyk()
            every { result.user?.uid } returns "user_id"
            every { result.user?.displayName } returns "User Name"
            every { result.user?.photoUrl } returns null
            every { result.user?.email } returns email
        }
        every { auth.createUserWithEmailAndPassword(email, password) } returns task

        mockkConstructor(FirebaseUser::class)

        val result = googleAuthClient.signInWithEmailAndPassword(email, password)

        assertThat(result.data).isEqualTo(
            UserData(
                userId = "user_id",
                userName = "User Name",
                email = email,
                profilePictureUrl = null
            )
        )
    }

    @Test
    fun `Logging in with email and password, signs in successfully`() = runTest {
        val email = "fellow@user.com"
        val password = "password"
        val task: Task<AuthResult> = spyk<Task<AuthResult>>().apply {
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns spyk()
            every { result.user?.uid } returns "user_id"
            every { result.user?.displayName } returns "User Name"
            every { result.user?.photoUrl } returns null
            every { result.user?.email } returns email
        }
        every { auth.signInWithEmailAndPassword(email, password) } returns task

        val result = googleAuthClient.loginWithEmailAndPassword(email, password)

        assertThat(result.data).isEqualTo(
            UserData(
                userId = "user_id",
                userName = "User Name",
                email = email,
                profilePictureUrl = null
            )
        )
    }

    @Test
    fun `Signing in with email and password, throws exception`() = runTest {
        val email = "fellow@user.com"
        val password = "password"

        every { auth.createUserWithEmailAndPassword(email, password) } throws Exception()

        assertFailure {
            googleAuthClient.signInWithEmailAndPassword(email, password)
        }
    }

    @Test
    fun `Logging in with email and password, throws exception`() = runTest {
        val email = "fellow@user.com"
        val password = "password"

        every { auth.signInWithEmailAndPassword(email, password) } throws Exception("Error message")

        assertFailure {
            googleAuthClient.signInWithEmailAndPassword(email, password)
        }
    }

    @Test
    fun `Logging out, logs out successfully`() = runTest {
        val task: Task<Void> = spyk<Task<Void>>().apply {
            every { isComplete } returns true
            every { isCanceled } returns false
        }
        every { oneTapClient.signOut() } returns task
        every { auth.signOut() } just Runs

        googleAuthClient.logout()

        coVerify {
            oneTapClient.signOut()
            auth.signOut()
        }
    }

    @Test
    fun `Logging out with client error, doesn't log out`() = runTest {
        every { oneTapClient.signOut() } throws Exception()

        googleAuthClient.logout()

        coVerify(inverse = true) {
            auth.signOut()
        }
    }

    @Test
    fun `Logging out with auth error, doesn't log out`() = runTest {
        coEvery { auth.signOut() } throws Exception()

        googleAuthClient.logout()

        coVerify {
            oneTapClient.signOut()
        }
    }

    @Test
    fun `Signing in with Google, returns the user`() = runTest {
        val intent = mockk<Intent>()
        val credential = mockk<SignInCredential>()

        every { oneTapClient.getSignInCredentialFromIntent(intent) } returns credential
        every { credential.googleIdToken } returns "google_id_token"

        val email = "fellow@user.com"

        val task: Task<AuthResult> = spyk<Task<AuthResult>>().apply {
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns spyk()
            every { result.user?.uid } returns "user_id"
            every { result.user?.displayName } returns "User Name"
            every { result.user?.photoUrl } returns null
            every { result.user?.email } returns email
        }

        every { auth.signInWithCredential(any()) } returns task

        val result = googleAuthClient.getSignInResultFromIntent(intent)

        assertThat(result.data).isEqualTo(
            UserData(
                userId = "user_id",
                userName = "User Name",
                email = email,
                profilePictureUrl = null
            )
        )
    }

    @Test
    fun `Sign in with Google with exception, return handled error message`() = runTest {
        val intent = mockk<Intent>()
        val credential = mockk<SignInCredential>()

        every { oneTapClient.getSignInCredentialFromIntent(intent) } returns credential
        every { credential.googleIdToken } returns "google_id_token"
        every { auth.signInWithCredential(any()) } throws Exception("Error message")

        val result = googleAuthClient.getSignInResultFromIntent(intent)

        assertThat(result.data).isNull()
        assertThat(result.errorMessage).isEqualTo("Error message")
    }

    @Test
    fun `Getting signed in user, returns the user`() {
        val email = "fellow@user.com"
        val user = mockk<FirebaseUser>()
        every { auth.currentUser } returns user
        every { user.uid } returns "user_id"
        every { user.displayName } returns "User Name"
        every { user.photoUrl } returns null
        every { user.email } returns email

        val userData = googleAuthClient.getSignedInUser()

        assertThat(userData).isEqualTo(
            UserData(
                userId = "user_id",
                userName = "User Name",
                email = email,
                profilePictureUrl = null
            )
        )
    }

    @Test
    fun `Getting signed in user when it doesn't exist, returns null`() {
        every { auth.currentUser } returns null

        val userData = googleAuthClient.getSignedInUser()

        assertThat(userData).isNull()
    }
}
