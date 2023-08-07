package com.example.feature_supervisor_research.indplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.eventusecases.AddEventUseCase
import com.example.core.model.Research
import com.example.core.researchUseCase.GetResearchByIdUseCase
import com.example.core.researchUseCase.UpdateResearchUseCase
import com.example.core.researchUseCase.UpdateTasksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class IndPlanViewModel @Inject constructor(
    private val getResearchByIdUseCase: GetResearchByIdUseCase,
    private val updateResearchUseCase: UpdateResearchUseCase

    ) : ViewModel() {

    private var _research = MutableLiveData<Research>()
    val research: LiveData<Research>
        get() = _research

    fun getResearchByIdUseCase(researchId: String) {
        viewModelScope.launch {
            _research.value = getResearchByIdUseCase.execute(researchId)
        }
    }

    fun updateResearch(research: Research, researchId: String) {
        viewModelScope.launch {
            updateResearchUseCase.execute(research)
            getResearchByIdUseCase(researchId)
        }
    }
}