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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCases: SignInUseCases
): ViewModel() {
    private val _state = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        repeatedPassword: String
    ) {
        viewModelScope.launch {
            try {
                val signInResult = useCases.signInWithEmailAndPasswordUseCase(
                    email,
                    password,
                    repeatedPassword
                )
                onSignInResult(signInResult)
            } catch (e: Exception) {
                e.message?.let {
                    _eventFlow.emit(UiEvent.ShowToast(it))
                }
            }
        }
    }

    fun loginWithEmailAndPassword(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            try {
                val signInResult = useCases.loginWithEmailAndPasswordUseCase(
                    email,
                    password
                )
                onSignInResult(signInResult)
            } catch (e: Exception) {
                e.message?.let {
                    _eventFlow.emit(UiEvent.ShowToast(it))
                }
            }
        }
    }

    fun getSignedInUser() = useCases.getSignedInUserUseCase()

    suspend fun signIn(): IntentSender? {
        return useCases.signInUseCase()
    }

    fun signInWithGoogle(data: Intent?) {
        viewModelScope.launch {
            try {
                val signInResult = useCases.signInWithGoogleUseCase(
                    data ?: return@launch
                )
                onSignInResult(signInResult)
            } catch (e: Exception) {
                e.message?.let {
                    _eventFlow.emit(UiEvent.ShowToast(it))
                }
            }
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
