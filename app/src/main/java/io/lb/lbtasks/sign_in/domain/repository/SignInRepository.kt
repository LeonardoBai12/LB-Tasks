package io.lb.lbtasks.sign_in.domain.repository

import android.content.Intent
import android.content.IntentSender
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.model.UserData

/**
 * Repository to communicate with the authentication client.
 */
interface SignInRepository {
    /**
     * Gets the currently signed in user, if it exists.
     * @return An [UserData] instance if there is an user signed in, null if there isn't.
     */
    fun getSignedInUser(): UserData?

    /**
     * Sign in with a new user.
     *
     * @param email User's email.
     * @param password User's password.
     *
     * @return A [SignInResult] instance with the resultant data.
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult

    /**
     * Login with an existent user.
     *
     * @param email User's email.
     * @param password User's password.
     *
     * @return A [SignInResult] instance with the resultant data.
     */
    suspend fun loginWithEmailAndPassword(email: String, password: String): SignInResult

    /**
     * Sign in with a Google account.
     *
     * @param data The intent on which the Google authentication will be displayed.
     */
    suspend fun signInWithGoogle(data: Intent): SignInResult

    /**
     * Sign in with Google callback.
     *
     * @return An intent sender as a callback from a Google sign in.
     */
    suspend fun signIn(): IntentSender?

    /**
     * Logout the currently signed in user.
     */
    suspend fun logout()
}
