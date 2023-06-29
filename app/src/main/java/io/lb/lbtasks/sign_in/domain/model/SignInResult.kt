package io.lb.lbtasks.sign_in.domain.model

data class SignInResult(
    val data: UserData?,
    val errorMessage: String? = null
)
