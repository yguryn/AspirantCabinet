package com.example.feature_administrator.aspirantlist.supervisorlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.aspirantusecase.GetAllAspirantsUseCase
import com.example.core.model.Aspirant
import com.example.core.model.Supervisor
import com.example.core.supervisorusecases.GetAllSupervisorsByFaculty
import kotlinx.coroutines.launch
import javax.inject.Inject

class SupervisorListViewModel @Inject constructor(
    private val getAllSupervisorsByFaculty: GetAllSupervisorsByFaculty,
) : ViewModel() {

    private var _supervisors = MutableLiveData<List<Supervisor>>()
    val supervisors: LiveData<List<Supervisor>>
        get() = _supervisors

    fun getAllSupervisors() {
        viewModelScope.launch {
            _supervisors.value = getAllSupervisorsByFaculty.execute()
        }
    }
}