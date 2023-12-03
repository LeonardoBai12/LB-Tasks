package io.lb.lbtasks.sign_in.domain.use_cases

import android.content.Intent
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

/**
 * Sign in with a Google account.
 *
 * @property repository Repository to communicate with the authentication client.
 */
class SignInWithGoogleUseCase(
    private val repository: SignInRepository
) {
    /**
     * Sign in with a Google account.
     *
     * @param data The intent on which the Google authentication will be displayed.
     */
    suspend operator fun invoke(data: Intent): SignInResult {
        return repository.signInWithGoogle(data)
    }
}
