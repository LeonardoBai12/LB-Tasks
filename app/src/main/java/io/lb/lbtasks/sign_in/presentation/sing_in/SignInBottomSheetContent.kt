package io.lb.lbtasks.sign_in.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.widgets.DefaultTextButton
import io.lb.lbtasks.core.presentation.widgets.DefaultTextField
import io.lb.lbtasks.core.util.showToast
import io.lb.lbtasks.sign_in.presentation.sing_in.SignInState

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun SignInBottomSheetContent(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val repeatedPassword = remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7F),
        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.enter_your_information),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            SignInTextFields(email, password, repeatedPassword)

            DefaultTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 72.dp, vertical = 16.dp),
                text = stringResource(id = R.string.sign_in),
                onClick = {

                },
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun SignInTextFields(
    email: MutableState<String>,
    password: MutableState<String>,
    repeatedPassword: MutableState<String>,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTextField(
            modifier = Modifier.padding(8.dp),
            text = email.value,
            icon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "LockIcon"
                )
            },
            label = "E-mail",
            onValueChange = {
                email.value = it
            }
        )

        DefaultTextField(
            modifier = Modifier.padding(8.dp),
            text = password.value,
            icon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "LockIcon"
                )
            },
            label = stringResource(id = R.string.password),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                password.value = it
            }
        )

        DefaultTextField(
            modifier = Modifier.padding(8.dp),
            text = password.value,
            icon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "LockIcon"
                )
            },
            label = stringResource(id = R.string.repeat_password),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                repeatedPassword.value = it
            }
        )
    }
}
