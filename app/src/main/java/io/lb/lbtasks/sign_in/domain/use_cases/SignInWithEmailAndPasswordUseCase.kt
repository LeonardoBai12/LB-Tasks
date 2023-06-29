package io.lb.lbtasks.sign_in.domain.use_cases

import io.lb.lbtasks.core.util.isValidEmail
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

class SignInWithEmailAndPasswordUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        repeatedPassword: String
    ): SignInResult {
        if (email.isValidEmail().not())
            throw Exception("Write a valid email")

        if (password.isBlank())
            throw Exception("Write your password")

        if (password != repeatedPassword)
            throw Exception("The passwords don't match")

        return repository.signInWithEmailAndPassword(email, password)
    }
}
