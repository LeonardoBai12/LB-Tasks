package io.lb.lbtasks.sign_in.data.auth_client

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.lb.lbtasks.R
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.model.UserData
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private  val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult {
        val result = try {
            auth.createUserWithEmailAndPassword(
                email,
                password
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

        return SignInResult(
            data = result?.user?.run {
                UserData(
                    userId = uid,
                    userName = displayName,
                    profilePictureUrl = photoUrl?.toString()
                )
            },
        )
    }

    suspend fun loginWithEmailAndPassword(email: String, password: String): SignInResult {
        val result = try {
            auth.signInWithEmailAndPassword(
                email,
                password
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

        return SignInResult(
            data = result?.user?.run {
                UserData(
                    userId = uid,
                    userName = displayName,
                    profilePictureUrl = photoUrl?.toString()
                )
            },
        )
    }

    suspend fun  signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun getSignInResultFromIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    suspend fun logout() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return  BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}
