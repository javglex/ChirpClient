package com.skymonkey.auth.presentation.register

// name these actions what the is happening in the UI.
// don't name them after what they do.
sealed interface RegisterScreenAction {
    data object OnLoginClick: RegisterScreenAction
    data object OnInputTextFocusGain: RegisterScreenAction
    data object OnRegisterClick: RegisterScreenAction
    data object OnTogglePasswordVisibilityClick: RegisterScreenAction
}