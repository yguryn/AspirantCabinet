package com.example.feature_schedule.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.eventusecases.GetAllEventsUseCase
import com.example.core.model.Event
import com.example.feature_schedule.schedule.model.DayOfMonthUI
import com.example.feature_schedule.utils.compareDate
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
) : ViewModel() {

    private var _daysOfMonth = MutableLiveData<List<DayOfMonthUI>>()
    val daysOfMonth: LiveData<List<DayOfMonthUI>>
        get() = _daysOfMonth

    private var _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>>
        get() = _events

    init {
        getDaysOfMonth(Date())
    }

    fun getAllEvents(calendar: Calendar) {
        viewModelScope.launch {
            val a = getAllEventsUseCase.execute()
            _events.value = a.filter { calendar.compareDate(it.event_start) }
            events.value?.forEach {
            }
        }
    }

    fun getDaysOfMonth(date: Date, currentMonth: Boolean = true) {

        val listDaysOfMonth = mutableListOf<DayOfMonthUI>()

        val currentDate = Date()

        val calendar = Calendar.getInstance().apply {
            time = date
        }

        val countOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val currentDay = SimpleDateFormat("d", Locale.getDefault()).format(currentDate)
            .filter { !it.isWhitespace() }

        for (item in 1 until countOfDaysInMonth + 1) {

            val year = SimpleDateFormat("y", Locale.getDefault()).format(date)
            val month = SimpleDateFormat("M", Locale.getDefault()).format(date)

            val dayOfMonthCalendar = GregorianCalendar(year.toInt(), month.toInt() - 1, item)

            val dayOfMonth = SimpleDateFormat("E", Locale.ENGLISH).format(dayOfMonthCalendar.time)

            if (currentDay == item.toString() && currentMonth) {
                listDaysOfMonth.add(
                    DayOfMonthUI(
                        item,
                        item,
                        dayOfMonth,
                        currentDay = true,
                        currentMonth = true
                    )
                )
            } else {
                listDaysOfMonth.add(
                    DayOfMonthUI(
                        item,
                        item,
                        dayOfMonth,
                        currentDay = false,
                        currentMonth = false
                    )
                )
            }
        }

        _daysOfMonth.value = listDaysOfMonth
    }

    fun modifyDaysOfMonth(
        dayOfMonthUI: DayOfMonthUI? = null,
        currentMonth: Boolean = true,
        numberOfMonth: Int? = null
    ) {
        if (currentMonth) {
            _daysOfMonth.value = _daysOfMonth.value!!.map {

                it.isPressed = it == dayOfMonthUI

                if (numberOfMonth != null) {
                    it.isPressed = it.day == numberOfMonth
                }

                it
            }
        } else {
            _daysOfMonth.value = _daysOfMonth.value!!.map {

                if (numberOfMonth != null) {
                    it.isPressed = it.day == numberOfMonth
                }

                it.currentMonth = false
                it
            }
        }
    }
}