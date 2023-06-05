package io.lb.lbtasks.feature_task.presentation.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.lb.lbtasks.core.presentation.widgets.DefaultIcon
import io.lb.lbtasks.core.presentation.widgets.SwipeableCard
import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.domain.model.TaskType

@ExperimentalMaterial3Api
@Composable
fun TaskCard(
    task: Task,
    onClickDelete: () -> Unit,
    onClickCard: (String) -> Unit,
) {
    SwipeableCard(
        onClickSwiped = {
            onClickDelete.invoke()
        },
        swipedContent = {
            Row(
                modifier = Modifier.padding(6.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        },
        onClickCard = {
            onClickCard.invoke(task.taskType)
        },
        cardContent = {
            TaskCardContent(task)
        }
    )
}

@Composable
private fun TaskCardContent(task: Task) {
    val showDescription = remember {
        mutableStateOf(false)
    }

    Column {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            DefaultIcon(
                modifier = Modifier.size(48.dp),
                iconModifier = Modifier.fillMaxSize(0.7F),
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.background.copy(0.9F),
                painter = painterResource(
                    id = TaskType.valueOf(task.taskType).painterId
                ),
                contentDescription = TaskType.valueOf(task.taskType).name,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(0.85F)
            ) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = "${task.deadlineDate?.replace("-", "/")}" +
                        " ${task.deadlineTime}",
                    fontSize = 12.sp
                )
            }

            task.description?.takeIf {
                it.isNotBlank()
            }?.let {
                IconButton(
                    onClick = {
                        showDescription.value = showDescription.value.not()
                    }
                ) {
                    Icon(
                        imageVector = if (showDescription.value)
                            Icons.Default.KeyboardArrowDown
                        else Icons.Default.KeyboardArrowRight,
                        contentDescription = "showDescription"
                    )
                }
            }
        }

        AnimatedVisibility(visible = showDescription.value) {
            Text(
                text = task.description ?: "",
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 12.dp),
                fontSize = 16.sp,
            )
        }
    }
}
