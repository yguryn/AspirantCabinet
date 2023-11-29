package com.example.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    fun Date.formatToString(): String {
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return format.format(this)
    }
}