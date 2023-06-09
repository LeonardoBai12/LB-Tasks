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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.presentation.details.TaskDetailsEvent
import io.lb.lbtasks.task.presentation.details.TaskDetailsScreen
import io.lb.lbtasks.task.presentation.details.TaskDetailsViewModel
import io.lb.lbtasks.task.presentation.listing.TaskEvent
import io.lb.lbtasks.task.presentation.listing.TaskViewModel
import io.lb.lbtasks.task.presentation.listing.TasksScreen
import io.lb.lbtasks.ui.theme.LBTasksTheme
import kotlinx.coroutines.flow.collectLatest
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
                    val signInState = signInViewModel.state.value

                    val taskViewModel = hiltViewModel<TaskViewModel>()
                    val taskState = taskViewModel.state.value

                    val taskDetailsViewModel = hiltViewModel<TaskDetailsViewModel>()
                    val taskDetailsState = taskDetailsViewModel.state.value

                    var startDestination = MainScreens.SignInScreen.name

                    signInViewModel.getSignedInUser()?.let {
                        startDestination = MainScreens.TaskScreen.name
                        taskViewModel.userData = it
                        taskDetailsViewModel.userData = it
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

                            LaunchedEffect(key1 = signInState.isSignInSuccessful) {
                                if (signInState.isSignInSuccessful) {
                                    applicationContext.showToast(R.string.sign_in_successful)

                                    navController.navigate(MainScreens.TaskScreen.name)
                                    signInViewModel.resetState()
                                }
                            }

                            LaunchedEffect(key1 = "launchedEffectKey") {
                                signInViewModel.eventFlow.collectLatest { event ->
                                    when (event) {
                                        is SignInViewModel.UiEvent.ShowToast -> {
                                            applicationContext.showToast(event.message)
                                        }
                                    }
                                }
                            }

                            SignInScreen(
                                state = signInState,
                                onSignInWithGoogleClick = {
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
                                },
                                onSignInClick = { email, password, repeatedPassword ->
                                    signInViewModel.signInWithEmailAndPassword(
                                        email = email,
                                        password = password,
                                        repeatedPassword = repeatedPassword
                                    )
                                },
                                onLoginClick = { email, password ->
                                    signInViewModel.loginWithEmailAndPassword(
                                        email = email,
                                        password = password,
                                    )
                                }
                            )
                        }

                        composable(MainScreens.TaskScreen.name) {
                            val userData = signInViewModel.getSignedInUser()

                            LaunchedEffect(key1 = "") {
                                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                                    taskViewModel.getTasks(userData ?: return@repeatOnLifecycle)
                                }
                            }

                            TasksScreen(
                                navController = navController,
                                userData = userData,
                                state = taskState,
                                onSignOut = {
                                    lifecycleScope.launch {
                                        signInViewModel.logout()
                                        taskViewModel.clear()
                                        applicationContext.showToast(R.string.signed_out)
                                        navController.navigate(MainScreens.SignInScreen.name) {
                                            popUpTo(MainScreens.TaskScreen.name) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                },
                                onRequestDelete = { task ->
                                    taskViewModel.onEvent(TaskEvent.RequestDelete(task))
                                },
                                onRestoreTask = {
                                    taskViewModel.onEvent(TaskEvent.RestoreTask)
                                },
                                onSearchTask = { filter ->
                                    taskViewModel.onEvent(TaskEvent.SearchedForTask(filter))
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
                            LaunchedEffect(key1 = MainScreens.TaskDetailsScreen.name) {
                                taskDetailsViewModel.eventFlow.collectLatest { event ->
                                    when (event) {
                                        is TaskDetailsViewModel.UiEvent.Finish -> {
                                            navController.navigateUp()
                                        }
                                        is TaskDetailsViewModel.UiEvent.ShowToast -> {
                                            applicationContext.showToast(event.message)
                                        }
                                    }
                                }
                            }

                            backStackEntry.arguments?.getString(TASK)?.let {
                                taskDetailsState.task = Task.fromJson(it)

                                TaskDetailsScreen(
                                    navController = navController,
                                    state = taskDetailsState,
                                    onRequestInsert = { title, description, date, time ->
                                        taskDetailsViewModel.onEvent(
                                            TaskDetailsEvent.RequestInsert(
                                                title = title,
                                                description = description,
                                                date = date,
                                                time = time,
                                            )
                                        )
                                    },
                                    onRequestUpdate = { title, description, date, time ->
                                        taskDetailsViewModel.onEvent(
                                            TaskDetailsEvent.RequestUpdate(
                                                title = title,
                                                description = description,
                                                date = date,
                                                time = time,
                                            )
                                        )
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
