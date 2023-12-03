package io.lb.lbtasks.core.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A single item shown on a navigation drawer menu.
 */
data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector,
)
