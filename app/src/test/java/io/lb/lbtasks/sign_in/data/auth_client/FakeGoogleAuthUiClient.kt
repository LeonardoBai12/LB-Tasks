package io.lb.lbtasks.sign_in.data.auth_client

import android.content.Intent
import android.content.IntentSender
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.sign_in.domain.use_cases.userData
import io.mockk.mockk

internal class FakeGoogleAuthUiClient : GoogleAuthClient {
    private var user: UserData? = null

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SignInResult {
        if (email == OLD_USER_EMAIL) {
            return SignInResult(data = null, errorMessage = "Email already in use.")
        }

        user = userData().copy(
            userId = "randomNewUserId",
            email = email,
        )

        return SignInResult(
            data = user,
            errorMessage = ""
        )
    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): SignInResult {
        if (email != OLD_USER_EMAIL) {
            return SignInResult(data = null, errorMessage = "No user with such email.")
        }

        if (password != OLD_USER_PASSWORD) {
            return SignInResult(data = null, errorMessage = "Wrong password.")
        }

        user = userData().copy(
            email = email,
        )

        return SignInResult(
            data = user,
            errorMessage = ""
        )
    }

    override suspend fun signIn(): IntentSender {
        return mockk()
    }

    override suspend fun getSignInResultFromIntent(intent: Intent): SignInResult {
        user = userData().copy(
            userId = "randomNewUserId",
            email = "someGoogleEmail@user.com",
        )

        return SignInResult(
            data = user,
            errorMessage = ""
        )
    }

    override fun getSignedInUser(): UserData? {
        return user
    }

    override suspend fun logout() {
        user = null
    }

    companion object {
        const val OLD_USER_EMAIL = "oldFellow@user.com"
        const val OLD_USER_PASSWORD = "OldUserPassword"
    }
}
