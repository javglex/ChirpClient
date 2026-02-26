package com.skymonkey.chirpclient

import androidx.compose.runtime.Composable
import com.skymonkey.auth.presentation.register.RegisterScreenRoot
import com.skymonkey.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        RegisterScreenRoot()
    }
}