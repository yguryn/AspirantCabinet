package com.example.feature_schedule.modifyevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.eventusecases.DeleteEventUseCase
import com.example.core.eventusecases.GetEventByIdUseCase
import com.example.core.eventusecases.ModifyEventUseCase
import com.example.core.model.Event
import com.example.core.utils.Constants.USER_ID
import com.example.core.utils.SharedPreferencesHelper
import javax.inject.Inject

class ModifyEventViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val modifyEventUseCase: ModifyEventUseCase,
    private val preferencesHelper: SharedPreferencesHelper,
    private val deleteEventUseCase: DeleteEventUseCase
) :
    ViewModel() {

    private var _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    fun getEventById(id: String) {
        getEventByIdUseCase.execute(id) {
            _event.value = it
        }
    }

    fun modifyEvent(event: Event) {
        modifyEventUseCase.execute(event)
    }

    fun deleteEvent(eventId: String) {
        deleteEventUseCase.execute(eventId)
    }

    fun getUserId() = preferencesHelper.getString(USER_ID)
}
