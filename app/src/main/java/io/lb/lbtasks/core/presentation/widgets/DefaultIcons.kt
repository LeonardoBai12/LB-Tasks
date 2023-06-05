package io.lb.lbtasks.core.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.lb.lbtasks.R

@Composable
fun LBTasksLogoIcon() {
    DefaultIcon(
        modifier = Modifier.size(60.dp),
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        contentDescription = "LoginHomeScreenIcon",
    )
}

@Composable
fun DefaultIcon(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier.fillMaxSize(),
    shape: Shape = RoundedCornerShape(24.dp),
    containerColor: Color = MaterialTheme.colorScheme.onPrimary,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    painter: Painter,
    contentDescription: String,
) {
    Box(
        modifier = modifier
            .background(
                color = containerColor,
                shape = shape
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = iconModifier,
            painter = painter,
            contentDescription = contentDescription,
            tint = contentColor
        )
    }
}
