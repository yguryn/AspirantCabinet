package com.example.feature_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.aspirantusecase.GetAspirantByIdUseCase
import com.example.core.model.Aspirant
import com.example.core.model.Research
import com.example.core.model.Supervisor
import com.example.core.researchUseCase.GetResearchByIdUseCase
import com.example.core.supervisorusecases.GetSupervisorByIdUseCase
import com.example.core.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val preferencesHelper: SharedPreferencesHelper,
    private val getAspirantByIdUseCase: GetAspirantByIdUseCase,
    private val getSupervisorByIdUseCase: GetSupervisorByIdUseCase,
    private val getResearchByIdUseCase: GetResearchByIdUseCase
) : ViewModel() {

    private var _aspirant = MutableLiveData<Aspirant>()
    val aspirant: LiveData<Aspirant>
        get() = _aspirant

    private var _supervisor = MutableLiveData<Supervisor>()
    val supervisor: LiveData<Supervisor>
        get() = _supervisor

    private var _research = MutableLiveData<Research>()
    val research: LiveData<Research>
        get() = _research

    fun getUserInfo() {
        val userId = preferencesHelper.getString("USER_ID")
        val userType = preferencesHelper.getString("USER_TYPE")
        if (userType == "Aspirant") {
            viewModelScope.launch {
                _aspirant.value = getAspirantByIdUseCase.execute(userId!!)
            }
        }
    }

    fun getSupervisorById(supervisorId: String){
        viewModelScope.launch {
            _supervisor.value = getSupervisorByIdUseCase.execute(supervisorId)
        }
    }

    fun getResearchById(researchId: String){
        viewModelScope.launch {
            _research.value = getResearchByIdUseCase.execute(researchId)
        }
    }
}