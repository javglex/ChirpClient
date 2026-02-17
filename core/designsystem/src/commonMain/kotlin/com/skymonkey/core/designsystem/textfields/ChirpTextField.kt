package com.skymonkey.core.designsystem.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.skymonkey.core.designsystem.theme.ChirpTheme
import com.skymonkey.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    title: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onFocusChanged: (Boolean) -> Unit = {},
) {

    ChirpTextFieldLayout(
        title = title,
        isError = isError,
        supportingText = supportingText,
        enabled = enabled,
        onFocusChanged = onFocusChanged,
        modifier = modifier
    ) { styleModifier, interactionSource ->
        BasicTextField(
            state = state,
            lineLimits = if(singleLine) {
                TextFieldLineLimits.SingleLine
            } else TextFieldLineLimits.Default,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if(enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else MaterialTheme.colorScheme.extended.textPlaceholder
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            modifier = styleModifier,
            decorator = { innerBox -> // innerbox holds the real textfield content
                // good for adding placeholders or hint texts
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (state.text.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.extended.textPlaceholder
                        )
                    }
                    innerBox()
                }
            }
        )
    }
}

@Composable
@Preview(
    showBackground = true
)
fun ChirpTextFieldEmptyPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier
                .width(300.dp),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Please enter your email address",
        )
    }
}

@Composable
@Preview(
    showBackground = true
)
fun ChirpTextFieldFilledPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(
                initialText = "test@test.com"
            ),
            modifier = Modifier
                .width(300.dp),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Please enter your email address",
        )
    }
}

@Composable
@Preview(
    showBackground = true
)fun ChirpTextFieldDisabledPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier
                .width(300.dp),
            placeholder = "test@test.com",
            enabled = false,
            title = "Email",
            supportingText = "Please enter your email address",
        )
    }
}

@Composable
@Preview(
    showBackground = true
)fun ChirpTextFieldErrorPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier
                .width(300.dp),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Not a valid email address.",
            isError = true,
        )
    }
}