package io.lb.lbtasks.sign_in.data.repository

import android.content.Intent
import android.content.IntentSender
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClient
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

class SignInRepositoryImpl(
    private val googleAuthClient: GoogleAuthClient
) : SignInRepository {
    override fun getSignedInUser() = googleAuthClient.getSignedInUser()

    override suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult {
        return googleAuthClient.signInWithEmailAndPassword(email, password)
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String): SignInResult {
        return googleAuthClient.loginWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithGoogle(data: Intent): SignInResult {
        return googleAuthClient.getSignInResultFromIntent(
            intent = data
        )
    }

    override suspend fun signIn(): IntentSender? {
        return googleAuthClient.signIn()
    }

    override suspend fun logout() {
        googleAuthClient.logout()
    }
}
