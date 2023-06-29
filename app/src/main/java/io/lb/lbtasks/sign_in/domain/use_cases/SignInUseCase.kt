package io.lb.lbtasks.sign_in.domain.use_cases

import android.content.IntentSender
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

class SignInUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(): IntentSender? {
        return repository.signIn()
    }
}
