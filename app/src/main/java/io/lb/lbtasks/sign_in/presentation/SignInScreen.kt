package io.lb.lbtasks.sign_in.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.widgets.DefaultTextButton
import io.lb.lbtasks.sign_in.presentation.login.LoginBottomSheetContent
import io.lb.lbtasks.sign_in.presentation.sing_in.SignInState
import io.lb.lbtasks.sign_in.presentation.widgets.HomeLoginBackground
import io.lb.lbtasks.sign_in.presentation.widgets.HomeLoginHeader
import io.lb.lbtasks.sign_in.presentation.widgets.SignInBottomSheetContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
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
            if (isLogin.value) LoginBottomSheetContent(state, onSignInClick)
            else SignInBottomSheetContent(state, onSignInClick)
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HomeLoginHeader()
            LoginButtonsColumn(coroutineScope, bottomSheetState, isLogin)
    }
    }
}

@ExperimentalMaterialApi
@Composable
private fun LoginButtonsColumn(
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    isLogin: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(72.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login),
            onClick = {
                coroutineScope.launch {
                    isLogin.value = true
                    bottomSheetState.show()
                }
            },
        )

        Spacer(modifier = Modifier.height(24.dp))

        DefaultTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.sign_in),
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary,
            onClick = {
                coroutineScope.launch {
                    isLogin.value = false
                    bottomSheetState.show()
                }
            },
        )
    }
}
