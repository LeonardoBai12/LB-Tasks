package io.lb.lbtasks.sign_in.presentation.sing_in

import android.content.Intent

sealed class SignInEvent {
    data class RequestSignIn(
        val email: String,
        val password: String,
        val repeatedPassword: String
    ) : SignInEvent()
    data class RequestLogin(val email: String, val password: String) : SignInEvent()
    data class RequestSignInWithGoogle(val data: Intent?) : SignInEvent()
    object LoadSignedInUser : SignInEvent()
    object RequestLogout : SignInEvent()
}
