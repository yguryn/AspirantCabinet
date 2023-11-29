package com.example.feature_schedule.newevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.eventusecases.AddEventUseCase
import com.example.core.model.Event
import com.example.core.utils.Constants.USER_ID
import com.example.core.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewEventViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    fun addEvent(event: Event) {
        viewModelScope.launch {
            addEventUseCase.execute(event)
        }
    }

    fun getUserId() =
        sharedPreferencesHelper.getString(USER_ID)

}