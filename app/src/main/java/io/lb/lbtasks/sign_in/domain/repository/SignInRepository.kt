package io.lb.lbtasks.sign_in.domain.repository

import android.content.Intent
import android.content.IntentSender
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.model.UserData

interface SignInRepository {
    fun getSignedInUser(): UserData?
    suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult
    suspend fun loginWithEmailAndPassword(email: String, password: String): SignInResult
    suspend fun signInWithGoogle(data: Intent): SignInResult
    suspend fun signIn(): IntentSender?
    suspend fun logout()
}
