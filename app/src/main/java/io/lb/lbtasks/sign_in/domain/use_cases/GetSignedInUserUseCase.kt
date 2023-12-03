package io.lb.lbtasks.sign_in.domain.use_cases

import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository

/**
 * Gets the currently signed in user, if it exists.
 *
 * @property repository Repository to communicate with the authentication client.
 */
class GetSignedInUserUseCase(
    private val repository: SignInRepository
) {
    /**
     * Gets the currently signed in user, if it exists.
     * @return An [UserData] instance if there is an user signed in, null if there isn't.
     */
    operator fun invoke(): UserData? {
        return repository.getSignedInUser()
    }
}
