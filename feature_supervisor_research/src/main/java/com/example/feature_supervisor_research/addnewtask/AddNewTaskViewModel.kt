package com.example.feature_supervisor_research.addnewtask

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.eventusecases.AddEventUseCase
import com.example.core.eventusecases.GetEventsForDateUseCase
import com.example.core.model.Event
import com.example.core.model.Research
import com.example.core.model.Task
import com.example.core.researchUseCase.GetResearchByIdUseCase
import com.example.core.researchUseCase.UpdateTasksUseCase
import com.example.core.utils.ResourceManager
import com.postgraduate.cabinet.ui.R
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddNewTaskViewModel @Inject constructor(
    private val getResearchByIdUseCase: GetResearchByIdUseCase,
    private val updateTasksUseCase: UpdateTasksUseCase,
    private val addEventUseCase: AddEventUseCase,
    private val getEventsForDateUseCase: GetEventsForDateUseCase,
    private val resourceManager: ResourceManager
) : ViewModel() {

    private var _research = MutableLiveData<Research>()
    val research: LiveData<Research>
        get() = _research

    fun addEvent(content: String, deadline: Date, aspirantId: String) {

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
            aspirantId,
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
                    user_id = aspirantId,
                    title = content,
                    description = resourceManager.getString(R.string.task_from_supervisor),
                    event_start = startTime,
                    event_end = endTime,
                )

                viewModelScope.launch {
                    addEventUseCase.execute(event)
                }
            } else {
                Toast.makeText(resourceManager.getContext(), resourceManager.getString(R.string.no_free_time_for_this_day), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getResearchByIdUseCase(researchId: String) {
        viewModelScope.launch {
            _research.value = getResearchByIdUseCase.execute(researchId)
        }
    }

    fun updateTasksUseCase(listOfTasks: List<Task>, researchId: String) {
        updateTasksUseCase.execute(listOfTasks, researchId)
    }
}