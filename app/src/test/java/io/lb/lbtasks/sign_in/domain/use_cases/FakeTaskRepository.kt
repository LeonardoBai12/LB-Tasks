package io.lb.lbtasks.sign_in.domain.use_cases

import android.content.Intent
import android.content.IntentSender
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import io.mockk.mockk

class FakeSignInRepository : SignInRepository {
    private var user: UserData? = null
    override fun getSignedInUser(): UserData? {
        return user
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult {
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

    override suspend fun loginWithEmailAndPassword(email: String, password: String): SignInResult {
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

    override suspend fun signInWithGoogle(data: Intent): SignInResult {
        user = UserData(
            userId = "randomGoogleUserId",
            userName = "Fellow User",
            email = "googlefellow@user.com",
            profilePictureUrl = null
        )

        return SignInResult(
            data = user,
            errorMessage = ""
        )
    }

    override suspend fun signIn(): IntentSender {
        return mockk(relaxed = true)
    }

    override suspend fun logout() {
        user = null
    }
}
