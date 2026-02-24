package com.skymonkey.core.designsystem.theme

import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

/**
* Usage: Create a single @PreviewLightDark function that takes a `darkTheme: Boolean` parameter,
* then call your composable with ChirpTheme(darkTheme = darkTheme).
*/
@Preview(name = "Light")
@Preview(name = "Dark")
annotation class PreviewLightDark

class BooleanPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(false, true)
}