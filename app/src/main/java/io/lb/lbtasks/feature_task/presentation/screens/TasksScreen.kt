package io.lb.lbtasks.feature_task.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import io.lb.lbtasks.core.presentation.widgets.LBTasksLogoIcon
import io.lb.lbtasks.feature_task.presentation.navigation.TaskScreens
import io.lb.lbtasks.feature_task.presentation.widgets.NewTaskBottomSheetContent
import io.lb.lbtasks.feature_task.presentation.widgets.TaskCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun TasksScreen(navController: NavHostController) {
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

    LaunchedEffect(key1 = "") {
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
                    TaskScreens.TaskDetailsScreen.name + "/$selectedTaskType"
                )
            }
        },
    ) {
        TasksScaffold(coroutineScope, bottomSheetState)
    }
}

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
private fun TasksScaffold(
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                    text = "LB Tasks",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 36.sp
                )
            }

            repeat(4) {
                TaskCard { }
            }
        }
    }
}
