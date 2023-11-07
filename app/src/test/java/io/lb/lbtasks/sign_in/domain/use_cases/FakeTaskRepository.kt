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
        user = userData().copy(
            userId = "randomNewUserId",
            email = email,
        )

        return SignInResult(
            data = user,
            errorMessage = ""
        )
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String): SignInResult {
        user = userData().copy(
            email = email,
        )

        return SignInResult(
            data = user,
            errorMessage = ""
        )
    }

    override suspend fun signInWithGoogle(data: Intent): SignInResult {
        user = userData().copy(
            userId = "randomGoogleUserId",
            email = "googlefellow@user.com",
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
