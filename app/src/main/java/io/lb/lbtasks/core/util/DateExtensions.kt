package io.lb.lbtasks.core.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String.toDateCalendar(): Calendar {
    val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    return Calendar.getInstance().apply {
        this.time = dateTimeFormat.parse(this@toDateCalendar)!!
    }
}

fun String.toTimeCalendar(): Calendar {
    val dateTimeFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    return Calendar.getInstance().apply {
        this.time  = dateTimeFormat.parse(this@toTimeCalendar)!!
    }
}


fun dateToString(
    day: Int,
    month: Int,
    year: Int,
): String {
    val date = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        .parse("$day-${month + 1}-$year")!!
    return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(date)
}

fun timeToString(
    hour: Int,
    minute: Int,
): String {
    val date = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        .parse("$hour:$minute")!!
    return SimpleDateFormat("HH:mm", Locale.ENGLISH).format(date)
}
