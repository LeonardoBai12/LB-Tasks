package io.lb.lbtasks.sign_in.domain.use_cases

import io.lb.lbtasks.core.util.isValidEmail
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

/**
 * Login with an existent user.
 *
 * @property repository Repository to communicate with the authentication client.
 */
class LoginWithEmailAndPasswordUseCase(
    private val repository: SignInRepository
) {
    /**
     * Login with an existent user.
     *
     * @param email User's email.
     * @param password User's password.
     *
     * @return A [SignInResult] instance with the resultant data.
     */
    suspend operator fun invoke(email: String, password: String): SignInResult {
        if (email.isValidEmail().not())
            throw Exception("Write a valid email")

        if (password.isBlank())
            throw Exception("Write your password")

        return repository.loginWithEmailAndPassword(email, password)
    }
}
