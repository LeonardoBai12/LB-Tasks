package io.lb.lbtasks.sign_in.domain

data class SignInResult(
    val data: UserData?,
    val errorMessage: String? = null
)

data class UserData(
    val userId: String?,
    val userName: String?,
    val profilePictureUrl: String?,
)
