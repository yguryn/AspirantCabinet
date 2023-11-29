package com.example.feature_events_list.feature_event_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.eventusecases.GetAllEventsUseCase
import com.example.core.model.Event
import com.example.core.utils.Constants.USER_TYPE
import com.example.core.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class EventListViewModel @Inject constructor(
    private val preferencesHelper: SharedPreferencesHelper,
    private val getAllEventsUseCase: GetAllEventsUseCase,
) : ViewModel() {

    private var _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>>
        get() = _events

    fun getUpcomingEventsById(countOfDays: Int) {
        viewModelScope.launch {
            val allEvents = getAllEventsUseCase.execute()
            _events.value =
                filterEventsByNextDays(allEvents, countOfDays).sortedBy { it.event_start }
        }
    }

    private fun filterEventsByNextDays(events: List<Event>, countOfDays: Int): List<Event> {
        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_YEAR, countOfDays)

        val next7Days = calendar.time

        val filteredEvents = ArrayList<Event>()

        for (event in events) {
            if (event.event_start in currentDate..next7Days) {
                filteredEvents.add(event)
            }
        }
        return filteredEvents
    }

    fun getUserType() = preferencesHelper.getString(USER_TYPE)
}