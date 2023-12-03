package io.lb.lbtasks.sign_in.domain.use_cases

import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

/**
 * Logout the currently signed in user.
 *
 * @property repository Repository to communicate with the authentication client.
 */
class LogoutUseCase(
    private val repository: SignInRepository,
) {
    /**
     * Logout the currently signed in user.
     */
    suspend operator fun invoke() {
        repository.logout()
    }
}
