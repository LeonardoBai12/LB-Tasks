package io.lb.lbtasks.core.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.navigation.MainScreens
import io.lb.lbtasks.core.util.TASK
import io.lb.lbtasks.core.util.showToast
import io.lb.lbtasks.sign_in.presentation.SignInScreen
import io.lb.lbtasks.sign_in.presentation.sing_in.GoogleAuthUiClient
import io.lb.lbtasks.sign_in.presentation.sing_in.SignInViewModel
import io.lb.lbtasks.task.presentation.details.TaskDetailsScreen
import io.lb.lbtasks.task.presentation.listing.TasksScreen
import io.lb.lbtasks.ui.theme.LBTasksTheme
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LBTasksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = viewModel<SignInViewModel>()
                    val state = viewModel.state.value

                    LaunchedEffect(key1 = Unit) {
                        googleAuthUiClient.getSignedInUser()?.let {
                            navController.navigate(MainScreens.TaskScreen.name)
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = MainScreens.SignInScreen.name
                    ) {
                        composable(MainScreens.SignInScreen.name) {
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult =
                                                googleAuthUiClient.getSignInResultFromIntent(
                                                    intent = result.data ?: return@launch
                                                )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    applicationContext.showToast(R.string.sign_in_successful)

                                    navController.navigate(MainScreens.TaskScreen.name)
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signIn()?.let {
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    it
                                                ).build()
                                            )
                                        } ?: applicationContext.showToast(
                                            R.string.something_went_wrong
                                        )
                                    }
                                }
                            )
                        }

                        composable(MainScreens.TaskScreen.name) {
                            TasksScreen(
                                navController = navController,
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        applicationContext.showToast(R.string.signed_out)
                                        navController.navigate(MainScreens.SignInScreen.name)
                                    }
                                }
                            )
                        }

                        composable(
                            route = MainScreens.TaskDetailsScreen.name + "/{$TASK}",
                            arguments = listOf(
                                navArgument(name = TASK) {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            backStackEntry.arguments?.getString(TASK)?.let {
                                TaskDetailsScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
