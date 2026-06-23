package com.skymonkey.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymonkey.auth.domain.EmailValidator
import com.skymonkey.core.domain.auth.AuthService
import com.skymonkey.core.domain.util.onFailure
import com.skymonkey.core.domain.util.onSuccess
import com.skymonkey.core.domain.validation.PasswordValidator
import com.skymonkey.core.presentation.util.toStringResource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

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
            RegisterScreenAction.OnRegisterClick -> register()
            RegisterScreenAction.OnTogglePasswordVisibilityClick -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
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

    private fun register() {
        if (!validateFormInputs()) return
        viewModelScope.launch {

            _state.update { it.copy(isRegistering = true) }

            val email = state.value.emailTextState.text.toString()
            val username = state.value.usernameTextState.text.toString()
            val password = state.value.passwordTextState.text.toString()

            authService
                .register(
                email = email,
                username = username,
                password = password
                )
                .onSuccess {
                    _state.update { it.copy(isRegistering = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(
                        isRegistering = false,
                        registrationError = error.toStringResource()
                    ) }
                }
        }
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