package com.example.feature_schedule.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import com.example.feature_schedule.schedule.ScheduleFragment.Companion.MINUTES_IN_HOUR
import com.example.feature_schedule.schedule.model.LocalTime
import com.example.feature_schedule.utils.Constants.HOURS_FORMAT
import com.example.feature_schedule.utils.Constants.HOURS_IN_DAY
import com.example.feature_schedule.utils.Constants.MINUTES_FORMAT
import com.example.feature_schedule.utils.Constants.PRESENT_DATE_FORMAT
import com.example.feature_schedule.utils.Constants.TIME_ROUNDING
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil


val formatForHours = SimpleDateFormat(HOURS_FORMAT, Locale.ENGLISH)
val formatForMinutes = SimpleDateFormat(MINUTES_FORMAT, Locale.ENGLISH)
val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
val sdf2 = SimpleDateFormat("E, d MMM", Locale.getDefault())

val Int.toPx: Float
    get() = this * Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

fun String.parseToLocalTime(): LocalTime {
    val time = this.split(":").map { it.toInt() }

    return LocalTime(time[0], time[1], 0, 0)
}

fun getRoundedTime(hours: Int, minutes: Int): Pair<Int, Int> {
    val roundingFactor = ceil(minutes / TIME_ROUNDING).toInt()
    var roundedMinutes = (roundingFactor * TIME_ROUNDING).toInt()

    var roundedHours = hours
    if (roundedMinutes >= MINUTES_IN_HOUR) {
        roundedMinutes = 0
        roundedHours = if (roundedHours == HOURS_IN_DAY - 1) 0 else roundedHours + 1
    }

    return Pair(roundedHours, roundedMinutes)
}

fun Pair<Int, Int>.hours() = this.first
fun Pair<Int, Int>.minutes() = this.second

fun getFormattedHours() = formatForHours.format(Date()).toInt()
fun getFormattedMinutes() = formatForMinutes.format(Date()).toInt()
fun getCalendarWithDate(): Calendar = Calendar.getInstance().apply {
    time = Date()
}

fun Calendar.setDate(year: Int, month: Int, dayOfMonth: Int) {
    set(Calendar.YEAR, year)
    set(Calendar.MONTH, month)
    set(Calendar.DAY_OF_MONTH, dayOfMonth)
}

fun Calendar.compareDate(date: Date): Boolean {
    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH)
    val day = this.get(Calendar.DAY_OF_MONTH)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val year2 = calendar.get(Calendar.YEAR)
    val month2 = calendar.get(Calendar.MONTH)
    val day2 = calendar.get(Calendar.DAY_OF_MONTH)
    return year == year2 && month == month2 && day == day2
}

fun Long.formatDateToPresent(): String =
    SimpleDateFormat(
        PRESENT_DATE_FORMAT,
        Locale.getDefault()
    ).format(this)

fun formatDateToTime(date: Date): String = sdf.format(date)

fun formatDateToWeek(date: Date): String = sdf2.format(date)


fun convertStringToDate(calendar: Calendar, timeString: String): Date {
    // Parse the time string using a SimpleDateFormat object
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val time = dateFormat.parse(timeString)

    // Set the hours and minutes of the Calendar object
    val timeCalendar = Calendar.getInstance()
    timeCalendar.time = time
    calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
    calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
    calendar.set(Calendar.SECOND, 0)

    // Return the Date object corresponding to the updated Calendar object
    return calendar.time
}

fun getTimeString(date: Date): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

fun main() {
    val dateString = "Tue, 11 Apr"
    val timeString = "08:00"
    val date: Date
//    date = convertStringToDate(dateString, timeString)
//    println(date)
}
