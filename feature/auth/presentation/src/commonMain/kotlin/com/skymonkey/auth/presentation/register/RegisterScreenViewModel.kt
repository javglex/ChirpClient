package com.skymonkey.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymonkey.auth.domain.EmailValidator
import com.skymonkey.core.domain.validation.PasswordValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RegisterScreenViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterScreenState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterScreenState()
        )

    fun onAction(action: RegisterScreenAction) {
        when (action) {
            RegisterScreenAction.OnLoginClick -> validateFormInputs()
            else -> {}
        }
    }

    private fun clearAllTextFieldErrors() {
        _state.update { it.copy(
            emailError = null,
            usernameError = null,
            passwordError = null,
            registrationError = null
        ) }
    }

    private fun validateFormInputs(): Boolean {
        clearAllTextFieldErrors()

        val currentState = state.value
        val email = currentState.emailTextState.text.toString()
        val username = currentState.usernameTextState.text.toString()
        val password = currentState.passwordTextState.text.toString()

        val isEmailValid = EmailValidator.validate(email)
        val passwordValidationState = PasswordValidator.validate(password)
        val isUsernameValid = username.length in 3..20

        val emailError = if(!isEmailValid) {
            RegistrationErrorType.InvalidEmail
        } else null
        val usernameError = if(!isUsernameValid) {
            RegistrationErrorType.InvalidUsername
        } else null
        val passwordError = if(!passwordValidationState.isValidPassword) {
            RegistrationErrorType.InvalidPassword
        } else null

        _state.update { it.copy(
            emailError = emailError,
            usernameError = usernameError,
            passwordError = passwordError
        ) }

        return isUsernameValid && isEmailValid && passwordValidationState.isValidPassword
    }
}