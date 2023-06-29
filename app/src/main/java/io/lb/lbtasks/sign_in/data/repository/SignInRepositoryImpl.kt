package io.lb.lbtasks.sign_in.data.repository

import android.content.Intent
import android.content.IntentSender
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthUiClient
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

class SignInRepositoryImpl(
    private val googleAuthUiClient: GoogleAuthUiClient
) : SignInRepository {
    override fun getSignedInUser() = googleAuthUiClient.getSignedInUser()

    override suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult {
        return googleAuthUiClient.signInWithEmailAndPassword(email, password)
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String): SignInResult {
        return googleAuthUiClient.loginWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithGoogle(data: Intent): SignInResult {
        return googleAuthUiClient.getSignInResultFromIntent(
            intent = data
        )
    }

    override suspend fun signIn(): IntentSender? {
        return googleAuthUiClient.signIn()
    }

    override suspend fun logout() {
        googleAuthUiClient.logout()
    }
}
