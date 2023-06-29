package io.lb.lbtasks.sign_in.domain.use_cases

import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

class LogoutUseCase(
    private val repository: SignInRepository,
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}
