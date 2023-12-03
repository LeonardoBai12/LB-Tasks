package io.lb.lbtasks.task.domain.model

import io.lb.lbtasks.R

/**
 * Types of task that are allowed to be created.
 */
enum class TaskType(
    val titleId: Int,
    val painterId: Int,
) {
    HOME(
        titleId = R.string.home,
        painterId = R.drawable.ic_home
    ),
    BUSINESS(
        titleId = R.string.business,
        painterId = R.drawable.ic_business
    ),
    STUDY(
        titleId = R.string.study,
        painterId = R.drawable.ic_school
    ),
    HOBBIES(
        titleId = R.string.hobbies,
        painterId = R.drawable.ic_esports
    ),
    SHOPPING(
        titleId = R.string.shopping,
        painterId = R.drawable.ic_shopping
    ),
    TRAVEL(
        titleId = R.string.travel,
        painterId = R.drawable.ic_travel
    ),
}
