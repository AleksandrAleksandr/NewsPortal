package com.example.newsportal.utils

import androidx.core.util.Pair
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

fun constructDatePicker(): MaterialDatePicker<Pair<Long, Long>> {
    val builder = MaterialDatePicker.Builder
        .dateRangePicker()
        .setTitleText("Select dates")
        .setSelection(Pair(MaterialDatePicker.todayInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
        .setCalendarConstraints(limitRange().build())
    return builder.build()
}

private fun limitRange(): CalendarConstraints.Builder {
    val today = MaterialDatePicker.todayInUtcMilliseconds()
    val calendar = Calendar.getInstance()

    calendar.set(calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DATE] - 30)
    val startDate = calendar.timeInMillis

    calendar.timeInMillis = today
    calendar[Calendar.MONTH] -= 1
    val startMonth = calendar.timeInMillis

    return CalendarConstraints.Builder()
        .setStart(startMonth)
        .setEnd(today)
        .setValidator(RangeValidator(startDate, today))
}

fun getDate(): String {
    return getCurrentDateTime().toString("yyyy-MM-dd")
}

private fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}