package com.example.feature_research.research

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.eventusecases.AddEventUseCase
import com.example.core.eventusecases.GetEventsForDateUseCase
import com.example.core.model.Event
import com.example.core.model.Research
import com.example.core.researchUseCase.GetAllResearchesUseCase
import com.example.core.researchUseCase.UpdateResearchUseCase
import com.example.core.utils.Constants.USER_ID
import com.example.core.utils.ResourceManager
import com.example.core.utils.SharedPreferencesHelper
import com.postgraduate.cabinet.ui.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ResearchViewModel @Inject constructor(
    private val getAllResearchesUseCase: GetAllResearchesUseCase,
    private val updateResearchUseCase: UpdateResearchUseCase,
    private val addEventUseCase: AddEventUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val getEventsForDateUseCase: GetEventsForDateUseCase,
    private val resourceManager: ResourceManager
) : ViewModel() {

    private val _listOfResearch = MutableStateFlow(Research())
    val listOfResearch: StateFlow<Research> = _listOfResearch.asStateFlow()

    init {
        getAllResearches()
    }

    fun getAllResearches() {
        viewModelScope.launch {
            _listOfResearch.value = getAllResearchesUseCase.execute()
        }
    }

    fun updateResearch(research: Research) {
        viewModelScope.launch {
            updateResearchUseCase.execute(research)
            _listOfResearch.value = getAllResearchesUseCase.execute()
        }
    }

    fun addEvent(content: String, deadline: Date, form: String) {
        val calendarForTimes = Calendar.getInstance().apply {
            time = deadline
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val allTimes = mutableListOf<Date>()
        for (hour in 6 until 24) {
            calendarForTimes.set(Calendar.HOUR_OF_DAY, hour)
            allTimes.add(calendarForTimes.time.clone() as Date)
        }

        getEventsForDateUseCase.execute(
            sharedPreferencesHelper.getString(USER_ID)!!,
            deadline
        ) { events ->

            for (event in events) {
                allTimes.removeAll { time ->
                    time.after(event.event_start) && time.before(event.event_end)
                }
            }

            val startTime = allTimes.firstOrNull()

            if (startTime != null) {

                val calendarForEvent = Calendar.getInstance().apply {
                    time = startTime
                    add(Calendar.HOUR_OF_DAY, 1)
                }
                val endTime = calendarForEvent.time

                val event = Event(
                    user_id = sharedPreferencesHelper.getString(USER_ID)!!,
                    title = content,
                    description = form,
                    event_start = startTime,
                    event_end = endTime,
                )

                viewModelScope.launch {
                    addEventUseCase.execute(event)
                }
            } else {
                Toast.makeText(
                    resourceManager.getContext(),
                    R.string.no_free_time_for_this_day,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}