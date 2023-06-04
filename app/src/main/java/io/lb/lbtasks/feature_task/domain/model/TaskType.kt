package io.lb.lbtasks.feature_task.domain.model

import io.lb.lbtasks.R

enum class TaskType(
    val title: String,
    val painterId: Int,
) {
    HOME(
        title = "Casa",
        painterId = R.drawable.ic_home
    ),
    BUSINESS(
        title = "Negócios",
        painterId = R.drawable.ic_business
    ),
    STUDY(
        title = "Estudos",
        painterId = R.drawable.ic_school
    ),
    HOBBIES(
        title = "Hobbies",
        painterId = R.drawable.ic_esports
    ),
    SHOPPING(
        title = "Compras",
        painterId = R.drawable.ic_shopping
    ),
    TRIP(
        title = "Viagem",
        painterId = R.drawable.ic_travel
    ),
}
