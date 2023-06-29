package io.lb.lbtasks.sign_in.domain.use_cases

import android.content.Intent
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

class SignInWithGoogleUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(data: Intent): SignInResult {
        return repository.signInWithGoogle(data)
    }
}
