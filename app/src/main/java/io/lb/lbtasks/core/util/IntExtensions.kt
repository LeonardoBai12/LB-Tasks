package io.lb.lbtasks.core.util

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Int.toDp(density: Density): Dp {
    return (this@toDp / density.density).dp
}
