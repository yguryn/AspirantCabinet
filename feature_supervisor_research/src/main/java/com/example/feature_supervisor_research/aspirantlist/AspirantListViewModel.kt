package com.example.feature_supervisor_research.aspirantlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.aspirantusecase.UpdateAspirantGradeUseCase
import com.example.core.model.Aspirant
import com.example.core.supervisorusecases.GetAspirantsBySupervisorUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AspirantListViewModel @Inject constructor(
    private val getAspirantsBySupervisorUseCase: GetAspirantsBySupervisorUseCase,
    private val updateAspirantGradeUseCase: UpdateAspirantGradeUseCase
) : ViewModel() {

    private var _aspirant = MutableLiveData<List<Aspirant>>()
    val aspirant: LiveData<List<Aspirant>>
        get() = _aspirant


    fun updateAspirantGrade(aspirant: Aspirant) {
        updateAspirantGradeUseCase.execute(aspirant)
    }

    fun check() {
        viewModelScope.launch {
            _aspirant.value = getAspirantsBySupervisorUseCase.execute()
        }
    }
}