package io.lb.lbtasks.sign_in.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.widgets.LBTasksLogoIcon

@Composable
fun HomeLoginBackground() {
    Image(
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.tools_picture),
        contentDescription = "LoginHomeScreenBackground",
    )
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
