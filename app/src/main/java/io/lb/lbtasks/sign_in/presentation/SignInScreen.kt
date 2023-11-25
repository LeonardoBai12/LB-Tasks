package io.lb.lbtasks.sign_in.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.widgets.DefaultTextButton
import io.lb.lbtasks.core.util.showToast
import io.lb.lbtasks.sign_in.presentation.login.LoginBottomSheetContent
import io.lb.lbtasks.sign_in.presentation.sing_in.SignInBottomSheetContent
import io.lb.lbtasks.sign_in.presentation.sing_in.SignInState
import io.lb.lbtasks.sign_in.presentation.widgets.HomeLoginBackground
import io.lb.lbtasks.sign_in.presentation.widgets.HomeLoginHeader
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun SignInScreen(
    onSignInWithGoogleClick: () -> Unit,
    onSignInClick: (String, String, String) -> Unit,
    onLoginClick: (String, String) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    val isLogin = remember {
        mutableStateOf(true)
    }

    HomeLoginBackground()

    BackHandler(enabled = bottomSheetState.isVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetContent = {
            if (isLogin.value) LoginBottomSheetContent(onLoginClick)
            else SignInBottomSheetContent(onSignInClick)
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HomeLoginHeader()
            LoginButtonsColumn(bottomSheetState, isLogin, onSignInWithGoogleClick)
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun LoginButtonsColumn(
    bottomSheetState: ModalBottomSheetState,
    isLogin: MutableState<Boolean>,
    onSignInWithGoogleClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(36.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            text = stringResource(id = R.string.login),
            onClick = {
                coroutineScope.launch {
                    isLogin.value = true
                    bottomSheetState.show()
                }
            },
        )

        DefaultTextButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.sign_in),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            onClick = {
                coroutineScope.launch {
                    isLogin.value = false
                    bottomSheetState.show()
                }
            },
        )

        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
            text = "or",
            fontWeight = FontWeight.SemiBold
        )

        DefaultTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(36.dp)),
            text = stringResource(id = R.string.continue_with_google),
            icon = painterResource(id = R.drawable.ic_google),
            containerColor = Color.White,
            contentColor = Color.DarkGray,
            onClick = {
                onSignInWithGoogleClick.invoke()
            },
        )
    }
}
