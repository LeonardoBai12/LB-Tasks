package io.lb.lbtasks.sign_in.presentation.sing_in

import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.use_cases.SignInUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCases: SignInUseCases
): ViewModel() {
    private val _state = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        repeatedPassword: String
    ) {
        viewModelScope.launch {

        }
    }

    fun loginWithEmailAndPassword() {
        viewModelScope.launch {

        }
    }

    fun getSignedInUser() = useCases.getSignedInUserUseCase()

    suspend fun signIn(): IntentSender? {
        return useCases.signInUseCase()
    }

    fun signInWithGoogle(data: Intent?) {
        viewModelScope.launch {
            val signInResult = useCases.signInWithGoogleUseCase(
                data ?: return@launch
            )
            onSignInResult(signInResult)
        }
    }

    private fun onSignInResult(result: SignInResult) {
        _state.value = _state.value.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )
    }

    fun logout() {
        viewModelScope.launch {
            useCases.logoutUseCase()
        }
    }

    fun resetState() {
        _state.value = SignInState()
    }
}
