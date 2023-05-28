package com.example.feature_research.research

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Research
import com.example.core.researchUseCase.AddResearchUseCase
import com.example.core.researchUseCase.GetAllResearchesUseCase
import com.example.core.researchUseCase.UpdateResearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResearchViewModel @Inject constructor(
    private val getAllResearchesUseCase: GetAllResearchesUseCase,
    private val addResearchUseCase: AddResearchUseCase,
    private val updateResearchUseCase: UpdateResearchUseCase
) :
    ViewModel() {

    private val _listOfResearch = MutableStateFlow(Research())
    val listOfResearch: StateFlow<Research> = _listOfResearch.asStateFlow()

    init {
        getAllResearches()
    }

    fun getAllResearches() {
        viewModelScope.launch {
            _listOfResearch.value = getAllResearchesUseCase.execute()
        }
    }

    fun updateResearch(research: Research) {
        viewModelScope.launch {
            updateResearchUseCase.execute(research)
            _listOfResearch.value = getAllResearchesUseCase.execute()
        }
    }

    fun addEvent(research: Research) {
        viewModelScope.launch {
            val result = addResearchUseCase.execute(research)
        }
    }
}