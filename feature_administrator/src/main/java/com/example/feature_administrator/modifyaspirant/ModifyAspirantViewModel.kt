package com.example.feature_administrator.modifyaspirant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.aspirantusecase.DeleteAspirantUseCase
import com.example.core.aspirantusecase.GetAspirantByIdUseCase
import com.example.core.aspirantusecase.ModifyAspirantUseCase
import com.example.core.model.Aspirant
import com.example.core.model.Supervisor
import com.example.core.supervisorusecases.AddSupervisorUseCase
import com.example.core.supervisorusecases.GetAllSupervisorsByFaculty
import com.example.core.supervisorusecases.GetSupervisorByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ModifyAspirantViewModel @Inject constructor(
    private val getAspirantByIdUseCase: GetAspirantByIdUseCase,
    private val getSupervisorByIdUseCase: GetSupervisorByIdUseCase,
    private val getAllSupervisorsByFaculty: GetAllSupervisorsByFaculty,
    private val modifyAspirantUseCase: ModifyAspirantUseCase,
    private val deleteAspirantUseCase: DeleteAspirantUseCase
) : ViewModel() {

    private var _aspirant = MutableLiveData<Aspirant>()
    val aspirant: LiveData<Aspirant>
        get() = _aspirant

    private var _supervisor = MutableLiveData<Supervisor>()
    val supervisor: LiveData<Supervisor>
        get() = _supervisor

    private var _supervisors = MutableLiveData<List<Supervisor>>()
    val supervisors: LiveData<List<Supervisor>>
        get() = _supervisors

    fun getAspirantById(aspirantId: String) {
        viewModelScope.launch {
            _aspirant.value = getAspirantByIdUseCase.execute(aspirantId)
        }
    }

    fun getSuperVisorById(supervisorId: String) {
        viewModelScope.launch {
            _supervisor.value = getSupervisorByIdUseCase.execute(supervisorId)
        }
    }

    fun getAllSupervisors() {
        viewModelScope.launch {
            _supervisors.value = getAllSupervisorsByFaculty.execute()
        }
    }

    fun modifyAspirant(aspirant: Aspirant) {
        modifyAspirantUseCase.execute(aspirant)
    }

    fun deleteAspirant(aspirantId: String) {
        deleteAspirantUseCase.execute(aspirantId)
    }
}