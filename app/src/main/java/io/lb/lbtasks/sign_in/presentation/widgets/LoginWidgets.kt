package io.lb.lbtasks.sign_in.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.widgets.LBTasksLogoIcon

@Composable
fun HomeLoginBackground() {
    val sizeImage = remember { mutableStateOf(IntSize.Zero) }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Black, Color.Transparent, Color.Black),
        startY = sizeImage.value.height.toFloat() / 12,
        endY = sizeImage.value.height.toFloat()
    )

    Box {
        Image(
            modifier = Modifier.fillMaxSize()
                .onGloballyPositioned {
                    sizeImage.value = it.size
                },
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "LoginHomeScreenBackground",
        )

        Box(modifier = Modifier.matchParentSize().background(gradient))
    }
}

@Composable
fun HomeLoginHeader() {
    Column(modifier = Modifier.padding(48.dp)) {
        LBTasksLogoIcon()
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 40.sp,
            color = Color.White
        )
    }
}
