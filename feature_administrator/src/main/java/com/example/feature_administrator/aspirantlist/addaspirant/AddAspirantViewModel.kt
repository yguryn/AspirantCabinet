package com.example.feature_administrator.aspirantlist.addaspirant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.aspirantusecase.AddAspirantUseCase
import com.example.core.model.Aspirant
import com.example.core.model.Supervisor
import com.example.core.supervisorusecases.GetAllSupervisorsByFaculty
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddAspirantViewModel @Inject constructor(
    private val addAspirantUseCase: AddAspirantUseCase,
    private val getAllSupervisorsByFaculty: GetAllSupervisorsByFaculty
) : ViewModel() {

    private var _supervisors = MutableLiveData<List<Supervisor>>()
    val supervisors: LiveData<List<Supervisor>>
        get() = _supervisors

    fun addAspirant(aspirant: Aspirant) {
        addAspirantUseCase.execute(aspirant)
    }

    fun getAllSupervisors() {
        viewModelScope.launch {
            _supervisors.value = getAllSupervisorsByFaculty.execute()
        }
    }
}