package io.lb.lbtasks.sign_in.data.repository

import android.content.Intent
import android.content.IntentSender
import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClient
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.model.UserData

class FakeGoogleAuthUiClient : GoogleAuthClient {
    private val user: UserData? = null

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SignInResult {

    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): SignInResult {

    }

    override suspend fun signIn(): IntentSender? {

    }

    override suspend fun getSignInResultFromIntent(intent: Intent): SignInResult {

    }

    override fun getSignedInUser(): UserData? {

    }

    override suspend fun logout() {

    }

}