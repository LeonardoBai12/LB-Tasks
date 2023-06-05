package io.lb.lbtasks.feature_task.presentation.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.widgets.LBTasksLogoIcon
import io.lb.lbtasks.core.util.DefaultSearchBar
import io.lb.lbtasks.core.util.showToast
import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.presentation.navigation.TaskScreens
import io.lb.lbtasks.feature_task.presentation.widgets.NewTaskBottomSheetContent
import io.lb.lbtasks.feature_task.presentation.widgets.TaskCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun TasksScreen(
    navController: NavHostController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val selectedTaskType = remember {
        mutableStateOf("")
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                selectedTaskType.value = ""
            }
            true
        }
    )
    val lifecycle = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = "launchedEffectKey") {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            if (bottomSheetState.isVisible)
                bottomSheetState.hide()
        }

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is TaskViewModel.UiEvent.ShowToast -> {
                    context.showToast(event.message)
                }
            }
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
                    TaskScreens.TaskDetailsScreen.name + "/${
                        Task(title = "", taskType = selectedTaskType.value).toJson()
                    }"
                )
            }
        },
    ) {
        TasksScaffold(
            navController = navController,
            coroutineScope = coroutineScope,
            bottomSheetState = bottomSheetState,
            viewModel = viewModel,
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
    viewModel: TaskViewModel,
) {
    val context = LocalContext.current
    val state = viewModel.state.value

    val search = remember {
        mutableStateOf("")
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                LBTasksLogoIcon()

                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 36.sp
                )
            }

            DefaultSearchBar(
                search = search,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                onSearch = { task ->
                    viewModel.onEvent(TaskEvent.SearchedForTask(task))
                },
            )

            LazyColumn {
                items(state.tasks) { task ->
                    TaskCard(
                        task = task,
                        onClickCard = {
                            navController.navigate(
                                TaskScreens.TaskDetailsScreen.name + "/${task.toJson()}"
                            )
                        },
                        onClickDelete = {
                            viewModel.onEvent(TaskEvent.RequestDelete(task))

                            scope.launch {
                                val result = snackBarHostState.showSnackbar(
                                    message = context.getString(R.string.task_deleted),
                                    actionLabel = context.getString(R.string.undo)
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(TaskEvent.RestoreTask)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
