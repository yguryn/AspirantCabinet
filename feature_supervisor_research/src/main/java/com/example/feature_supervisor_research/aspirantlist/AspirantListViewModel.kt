package com.example.feature_supervisor_research.aspirantlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.aspirantusecase.AddAspirantUseCase
import com.example.core.model.Aspirant
import com.example.core.model.Research
import com.example.core.researchUseCase.GetResearchByIdUseCase
import com.example.core.supervisorusecases.AddSupervisorUseCase
import com.example.core.supervisorusecases.GetAspirantsBySupervisorUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AspirantListViewModel @Inject constructor(
    private val getAspirantsBySupervisorUseCase: GetAspirantsBySupervisorUseCase,
    private val getResearchByIdUseCase: GetResearchByIdUseCase
) : ViewModel() {

    private var _aspirant = MutableLiveData<List<Aspirant>>()
    val aspirant: LiveData<List<Aspirant>>
        get() = _aspirant


//    fun addUsers() {
//        addAspirantUseCase.execute()
//        addSupervisorUseCase.execute()
//    }

    fun check() {
        viewModelScope.launch {
            _aspirant.value = getAspirantsBySupervisorUseCase.execute()
        }
    }
}