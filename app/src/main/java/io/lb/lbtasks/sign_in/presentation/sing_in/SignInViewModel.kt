package io.lb.lbtasks.sign_in.presentation.sing_in

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.lbtasks.sign_in.domain.model.SignInResult
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.sign_in.domain.use_cases.SignInUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCases: SignInUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentUser: UserData? = null

    init {
        getSignedInUser()
    }

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.RequestSignInWithGoogle -> {
                signInWithGoogle(event.data)
            }
            SignInEvent.LoadSignedInUser -> {
                getSignedInUser()
            }
            is SignInEvent.RequestLogin -> {
                loginWithEmailAndPassword(
                    event.email,
                    event.password
                )
            }
            SignInEvent.RequestLogout -> {
                logout()
            }
            is SignInEvent.RequestSignIn -> {
                signInWithEmailAndPassword(
                    event.email,
                    event.password,
                    event.repeatedPassword
                )
            }
        }
    }

    private fun signInWithEmailAndPassword(
        email: String,
        password: String,
        repeatedPassword: String
    ) {
        viewModelScope.launch {
            val result = try {
                val signInResult = useCases.signInWithEmailAndPasswordUseCase(
                    email,
                    password,
                    repeatedPassword
                )
                signInResult
            } catch (e: Exception) {
                SignInResult(data = null, errorMessage = e.message)
            }
            onSignInResult(result)
        }
    }

    private fun loginWithEmailAndPassword(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            val result = try {
                val signInResult = useCases.loginWithEmailAndPasswordUseCase(
                    email,
                    password
                )
                signInResult
            } catch (e: Exception) {
                SignInResult(data = null, errorMessage = e.message)
            }
            onSignInResult(result)
        }
    }

    private fun getSignedInUser() {
        currentUser = useCases.getSignedInUserUseCase()
    }

    suspend fun signIn(): IntentSender? {
        return useCases.signInUseCase()
    }

    private fun signInWithGoogle(data: Intent?) {
        viewModelScope.launch {
            val result = try {
                val signInResult = useCases.signInWithGoogleUseCase(
                    data ?: return@launch
                )
                signInResult
            } catch (e: Exception) {
               SignInResult(data = null, errorMessage = e.message)
            }
            onSignInResult(result)
        }
    }

    private fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }

        result.errorMessage?.takeIf {
            it.isNotBlank()
        }?.let {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowToast(it))
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            useCases.logoutUseCase()
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}
