package com.example.feature_supervisor_research

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToString(): String {
    val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return format.format(this)
}