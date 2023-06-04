package io.lb.lbtasks.core.presentation.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    icon: @Composable (() -> Unit)? = null,
    isEnabled: Boolean = true,
    isSingleLined: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    onValueChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = modifier,
        value = text,
        singleLine = isSingleLined,
        leadingIcon = icon,
        enabled = isEnabled,
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Color.Transparent,
        ),
        label = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction.invoke()
                keyboardController?.hide()
            }
        ),
        onValueChange = {
            onValueChange.invoke(it)
        }
    )
}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun DefaultFilledTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    label: String,
    icon: @Composable (() -> Unit)? = null,
    isEnabled: Boolean = true,
    isSingleLined: Boolean = true,
    hasCloseButton: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    onImeAction: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            textAlign = TextAlign.Start,
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text.value,
            shape = RoundedCornerShape(8.dp),
            singleLine = isSingleLined,
            leadingIcon = icon,
            enabled = isEnabled,
            trailingIcon = if (hasCloseButton && text.value.isNotEmpty()) {
                { CloseButton(text) }
            } else null,
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction.invoke()
                    keyboardController?.hide()
                }
            ),
            onValueChange = {
                text.value = it
            },
        )
    }
}

@Composable
private fun CloseButton(text: MutableState<String>) {
    Icon(
        Icons.Default.Close,
        modifier = Modifier.clickable {
            text.value = ""
        },
        contentDescription = "closeIcon",
    )
}
