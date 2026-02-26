package com.skymonkey.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState

data class RegisterScreenState(
    // email fields
    val emailTextState: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val emailError: RegistrationErrorType? = null,
    // password fields
    val passwordTextState: TextFieldState = TextFieldState(),
    val isPasswordValid: Boolean = false,
    val passwordError: RegistrationErrorType? = null,
    val isPasswordVisible: Boolean = false,
    // username fields
    val usernameTextState: TextFieldState = TextFieldState(),
    val isUsernameValid: Boolean = false,
    val usernameError: RegistrationErrorType? = null,
    // registration button
    val registrationError: RegistrationErrorType? = null,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)

sealed interface RegistrationErrorType {
    data object UsernameExists: RegistrationErrorType
    data object InvalidUsername: RegistrationErrorType
    data object EmailExists: RegistrationErrorType
    data object InvalidEmail: RegistrationErrorType
    data object InvalidPassword: RegistrationErrorType
}