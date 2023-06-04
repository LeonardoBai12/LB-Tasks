package io.lb.lbtasks.core.extensions

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.MutableState
import io.lb.lbtasks.R
import java.text.SimpleDateFormat
import java.util.*

fun Context.timePicker(time: MutableState<String>, isDarkTheme: Boolean): TimePickerDialog {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    val theme = if (isDarkTheme) {
        R.style.Theme_DateDialogDark
    } else {
        R.style.Theme_DateDialogLight
    }

    return TimePickerDialog(
        this,
        theme,
        {_, hour, minute ->
            time.value = timeToString(hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
}

fun Context.datePicker(date: MutableState<String>, isDarkTheme: Boolean): DatePickerDialog {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    val theme = if (isDarkTheme) {
        R.style.Theme_DateDialogDark
    } else {
        R.style.Theme_DateDialogLight
    }

    return DatePickerDialog(
        this,
        theme,
        { _, year, month, day ->
            date.value = dateToString(day, month, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}

private fun dateToString(
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
