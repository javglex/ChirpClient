package com.skymonkey.core.designsystem.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.skymonkey.core.designsystem.components.brand.ChirpBrandLogo
import com.skymonkey.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.skymonkey.core.designsystem.theme.BooleanPreviewParameterProvider
import com.skymonkey.core.designsystem.theme.ChirpTheme


@Composable
@PreviewScreenSizes
fun ChirpAdaptiveFormLayoutPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) darkTheme: Boolean
) {
    ChirpTheme(darkTheme = darkTheme) {
        ChirpAdaptiveFormLayout(
            headerText = "Welcome to chirp!",
            errorText = "Login failed!",
            logo = { ChirpBrandLogo() },
            formContent = {
                Text(
                    text = "Sample form title",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Sample form title 2",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        )
    }
}