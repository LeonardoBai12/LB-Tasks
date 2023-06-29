package io.lb.lbtasks.core.presentation

import android.os.Bundle
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.navigation.MainScreens
import io.lb.lbtasks.core.util.TASK
import io.lb.lbtasks.core.util.showToast
import io.lb.lbtasks.sign_in.presentation.SignInScreen
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
                    val signInViewModel = hiltViewModel<SignInViewModel>()
                    val state = signInViewModel.state.value

                    var startDestination = MainScreens.SignInScreen.name

                    signInViewModel.getSignedInUser()?.let {
                        startDestination = MainScreens.TaskScreen.name
                    }

                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable(MainScreens.SignInScreen.name) {
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        signInViewModel.signInWithGoogle(result.data)
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    applicationContext.showToast(R.string.sign_in_successful)

                                    navController.navigate(MainScreens.TaskScreen.name)
                                    signInViewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        signInViewModel.signIn()?.let {
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
                                userData = signInViewModel.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        signInViewModel.logout()
                                        applicationContext.showToast(R.string.signed_out)
                                        navController.navigate(MainScreens.SignInScreen.name) {
                                            popUpTo(MainScreens.TaskScreen.name) {
                                                inclusive = true
                                            }
                                        }
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
