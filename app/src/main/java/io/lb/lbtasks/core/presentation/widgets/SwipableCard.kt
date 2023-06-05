package io.lb.lbtasks.core.presentation.widgets

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.lb.lbtasks.core.util.toDp
import io.lb.lbtasks.ui.theme.CardBackgroundDark
import io.lb.lbtasks.ui.theme.CardBackgroundLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalMaterial3Api
@Composable
fun SwipeableCard(
    onClickSwiped: () -> Unit,
    swipedContent: @Composable (()-> Unit),
    onClickCard: () -> Unit,
    cardContent: @Composable (ColumnScope.()-> Unit),
) {
    val isSwiped = remember {
        mutableStateOf(false)
    }

    val transitionState = remember {
        MutableTransitionState(isSwiped).apply {
            targetState.value = isSwiped.value
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")

    val offsetTransition = transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = {
            tween(durationMillis = 300)
        },
        targetValueByState = { target ->
            target.value = isSwiped.value

            if (isSwiped.value) 85F
            else 0F
        },
    )

    val swipeHeight = remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()

    Surface {
        if (offsetTransition.value > 0) {
            Surface(
                modifier = Modifier
                    .height(swipeHeight.value.toDp(LocalDensity.current))
                    .fillMaxWidth(0.3F)
                    .padding(horizontal = 12.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.errorContainer,
                onClick = {
                    scope.launch {
                        isSwiped.value = false
                        delay(300)
                        onClickSwiped.invoke()
                    }
                },
                content = swipedContent
            )
        }

        DefaultCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
                .offset {
                    IntOffset(offsetTransition.value.roundToInt(), 0)
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        when {
                            dragAmount >= 6 -> {
                                isSwiped.value = true
                            }

                            dragAmount < -6 -> {
                                isSwiped.value = false
                            }
                        }
                    }
                }
                .layout { measurable, constraints ->
                    with(measurable.measure(constraints)) {
                        swipeHeight.value = height

                        layout(width, height) {
                            placeRelative(0, 0)
                        }
                    }
                },
            onClick = {
                onClickCard.invoke()
            },
            content = cardContent
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun DefaultCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable (ColumnScope.()-> Unit),
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme())
                CardBackgroundDark
            else CardBackgroundLight
        ),
        onClick = {
            onClick.invoke()
        },
        content = content
    )
}
