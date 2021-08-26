package com.example.newsportal.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(this)
}

fun String.toFormat(): String {
    return replace('-', '.')
}