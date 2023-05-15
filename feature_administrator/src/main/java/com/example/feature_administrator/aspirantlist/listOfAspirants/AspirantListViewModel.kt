package com.example.feature_administrator.aspirantlist.listOfAspirants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.aspirantusecase.GetAllAspirantsUseCase
import com.example.core.eventusecases.GetAllEventsUseCase
import com.example.core.model.Aspirant
import com.example.core.model.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class AspirantListViewModel @Inject constructor(
    private val getAllAspirantsUseCase: GetAllAspirantsUseCase,
) : ViewModel() {

    private var _aspirants = MutableLiveData<List<Aspirant>>()
    val aspirants: LiveData<List<Aspirant>>
        get() = _aspirants

    fun getAllAspirants() {
        viewModelScope.launch {
            _aspirants.value = getAllAspirantsUseCase.execute()
        }
    }
}