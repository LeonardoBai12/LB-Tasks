package io.lb.lbtasks.sign_in.data.repository

import io.lb.lbtasks.sign_in.data.auth_client.GoogleAuthClient
import io.lb.lbtasks.sign_in.domain.repository.SignInRepository
import org.junit.jupiter.api.BeforeEach

class SignInRepositoryImplTest {
    private lateinit var authClient: GoogleAuthClient
    private lateinit var repository: SignInRepository

    @BeforeEach
    fun setUp() {

    }
}