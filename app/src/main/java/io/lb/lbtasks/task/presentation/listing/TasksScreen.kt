package io.lb.lbtasks.task.presentation.listing

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.navigation.DrawerBody
import io.lb.lbtasks.core.presentation.navigation.DrawerHeader
import io.lb.lbtasks.core.presentation.navigation.MainScreens
import io.lb.lbtasks.core.presentation.navigation.MenuItem
import io.lb.lbtasks.core.presentation.widgets.AppBar
import io.lb.lbtasks.core.util.DefaultSearchBar
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.presentation.widgets.NewTaskBottomSheetContent
import io.lb.lbtasks.task.presentation.widgets.TaskCard
import io.lb.lbtasks.task.presentation.widgets.TaskShimmerCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun TasksScreen(
    navController: NavHostController,
    userData: UserData?,
    state: TaskState,
    onSignOut: () -> Unit,
    onRequestDelete: (Task) -> Unit,
    onRestoreTask: () -> Unit,
    onSearchTask: (String) -> Unit,
) {
    val selectedTaskType = remember {
        mutableStateOf("")
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                selectedTaskType.value = ""
            }
            true
        }
    )
    val lifecycle = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(key1 = "launchedEffectKey") {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            if (bottomSheetState.isVisible)
                bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetContent = {
            NewTaskBottomSheetContent(selectedTaskType) {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }
                navController.navigate(
                    MainScreens.TaskDetailsScreen.name +
                        "/${Task(title = "", taskType = selectedTaskType.value).toJson()}"
                )
            }
        },
    ) {
        TasksScaffold(
            navController = navController,
            coroutineScope = coroutineScope,
            bottomSheetState = bottomSheetState,
            userData = userData,
            state = state,
            onRequestDelete = onRequestDelete,
            onRestoreTask = onRestoreTask,
            onSearchTask = onSearchTask,
            onSignOut = onSignOut,
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
private fun TasksScaffold(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    userData: UserData?,
    state: TaskState,
    onRequestDelete: (Task) -> Unit,
    onRestoreTask: () -> Unit,
    onSearchTask: (String) -> Unit,
    onSignOut: () -> Unit,
) {
    val context = LocalContext.current
    val search = remember {
        mutableStateOf("")
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader(userData = userData)
                DrawerBody(
                    items = listOf(
                        MenuItem(
                            id = "Home",
                            title = stringResource(id = R.string.home),
                            contentDescription = "Home Button",
                            icon = Icons.Default.Home
                        ),
                        MenuItem(
                            id = "Logout",
                            title = stringResource(id = R.string.logout),
                            contentDescription = "Logout Button",
                            icon = Icons.Default.ExitToApp
                        )
                    ),
                    onItemClick = {
                        when (it.id) {
                            "Home" -> {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                            }

                            "Logout" -> {
                                onSignOut.invoke()
                            }
                        }
                    }
                )
            }
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBar {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "HomeFAB",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultSearchBar(
                    search = search,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                    onSearch = { filter ->
                        onSearchTask.invoke(filter)
                    },
                )

                LazyColumn {
                    if (state.loading) {
                        items(3) {
                            TaskShimmerCard()
                        }
                    } else {
                        items(state.tasks) { task ->
                            TaskCard(
                                task = task,
                                onClickCard = {
                                    navController.navigate(
                                        MainScreens.TaskDetailsScreen.name + "/${task.toJson()}"
                                    )
                                },
                                onClickDelete = {
                                    onRequestDelete.invoke(task)

                                    coroutineScope.launch {
                                        val result = snackBarHostState.showSnackbar(
                                            message = context.getString(R.string.task_deleted),
                                            actionLabel = context.getString(R.string.undo)
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            onRestoreTask.invoke()
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
