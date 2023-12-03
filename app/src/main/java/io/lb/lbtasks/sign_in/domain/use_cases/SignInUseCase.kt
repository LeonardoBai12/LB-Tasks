package io.lb.lbtasks.sign_in.domain.use_cases

import android.content.IntentSender
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

/**
 * Sign in with Google callback.
 *
 * @property repository Repository to communicate with the authentication client.
 */
class SignInUseCase(
    private val repository: SignInRepository
) {
    /**
     * Sign in with Google callback.
     *
     * @return An intent sender as a callback from a Google sign in.
     */
    suspend operator fun invoke(): IntentSender? {
        return repository.signIn()
    }
}
