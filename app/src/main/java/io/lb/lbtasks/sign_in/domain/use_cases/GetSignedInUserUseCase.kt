package io.lb.lbtasks.sign_in.domain.use_cases

import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

class GetSignedInUserUseCase(
    private val repository: SignInRepository
) {
    operator fun invoke(): UserData? {
        return repository.getSignedInUser()
    }
}
