package io.lb.lbtasks.core.util

import io.lb.lbtasks.task.domain.model.Task

fun List<Task>.filterBy(filter: String) =
    filter {
        filter.lowercase() in it.title.lowercase() ||
            filter.lowercase() in (it.description?.lowercase() ?: "")
    }
