package io.lb.lbtasks.core.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultIconButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier.fillMaxSize(),
    shape: Shape = RoundedCornerShape(24.dp),
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = shape
            ),
        onClick = onClick,
    ) {
        Icon(
            modifier = iconModifier,
            painter = painter,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    icon: Painter? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(48.dp),
        enabled = enabled,
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                    painter = it,
                    contentDescription = "SignInButton",
                    tint = Color.Unspecified
                )
            }
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}
