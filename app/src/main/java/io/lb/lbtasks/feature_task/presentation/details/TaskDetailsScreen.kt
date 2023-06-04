package io.lb.lbtasks.feature_task.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.lb.lbtasks.R
import io.lb.lbtasks.core.util.createDatePickerDialog
import io.lb.lbtasks.core.util.createTimePickerDialog
import io.lb.lbtasks.core.presentation.widgets.DefaultFilledTextField
import io.lb.lbtasks.core.presentation.widgets.DefaultTextButton
import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.presentation.listing.TaskEvent
import io.lb.lbtasks.feature_task.presentation.listing.TaskViewModel

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun TaskDetailsScreen(
    navController: NavHostController,
    viewModel: TaskDetailsVIewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    val title = remember {
        mutableStateOf(state.task?.title  ?: "")
    }

    val description = remember {
        mutableStateOf(state.task?.description ?: "")
    }

    val date = remember {
        mutableStateOf(state.task?.deadlineDate ?: "")
    }

    val time = remember {
        mutableStateOf(state.task?.deadlineTime ?: "")
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Arrow Back",
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.new_task),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            DefaultFilledTextField(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = title,
                label = stringResource(R.string.title),
            )

            DefaultFilledTextField(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = description,
                isSingleLined = false,
                label = stringResource(R.string.description),
            )

            DateAndTimePickers(date, time)

            DefaultTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 72.dp, vertical = 16.dp),
                text = stringResource(R.string.finish)
            ) {
                viewModel.onEvent(
                    TaskDetailsEvent.RequestInsert(
                        title = title.value,
                        description = description.value,
                        date = date.value,
                        time = time.value,
                    )
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
private fun DateAndTimePickers(
    date: MutableState<String>,
    time: MutableState<String>,
) {
    val context = LocalContext.current
    val datePicker = context.createDatePickerDialog(date, isSystemInDarkTheme())
    val timePicker = context.createTimePickerDialog(time, isSystemInDarkTheme())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        DefaultFilledTextField(
            modifier = Modifier.fillMaxWidth(0.55f),
            text = date,
            isSingleLined = false,
            isEnabled = false,
            hasCloseButton = true,
            label = stringResource(id = R.string.deadline),
            icon = {
                Icon(
                    Icons.Default.DateRange,
                    modifier = Modifier.clickable {
                        datePicker.show()
                    },
                    contentDescription = "dateIcon"
                )
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        DefaultFilledTextField(
            modifier = Modifier.fillMaxWidth(),
            text = time,
            isSingleLined = false,
            isEnabled = false,
            hasCloseButton = true,
            label = "",
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock),
                    modifier = Modifier.clickable {
                        timePicker.show()
                    },
                    contentDescription = "timeIcon"
                )
            },
        )
    }
}
