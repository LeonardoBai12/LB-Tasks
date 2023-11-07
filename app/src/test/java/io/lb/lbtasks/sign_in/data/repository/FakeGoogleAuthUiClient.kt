package io.lb.lbtasks.sign_in.data.repository

import android.content.Intent
import android.content.IntentSender
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClient
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.mockk.mockk

class FakeGoogleAuthUiClient : GoogleAuthClient {
    private var user: UserData? = null

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SignInResult {
        if (email == OLD_USER_EMAIL) {
            return SignInResult(data = null, errorMessage = "Email already in use.")
        }

        user = UserData(
            userId = "randomNewUserId",
            userName = "Fellow User",
            email = email,
            profilePictureUrl = null
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

        user = UserData(
            userId = "randomOldUserId",
            userName = "Fellow User",
            email = email,
            profilePictureUrl = null
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
        user = UserData(
            userId = "randomNewUserId",
            userName = "Fellow User",
            email = "someGoogleEmail@user.com",
            profilePictureUrl = null
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
