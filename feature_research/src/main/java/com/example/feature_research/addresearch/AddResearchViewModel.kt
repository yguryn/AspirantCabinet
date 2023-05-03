package com.example.feature_research.addresearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Event
import com.example.core.model.Research
import com.example.core.researchUseCase.AddResearchUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddResearchViewModel @Inject constructor(
    private val addResearchUseCase: AddResearchUseCase
) : ViewModel() {

    fun addEvent(research: Research) {
        viewModelScope.launch {
            addResearchUseCase.execute(research)
        }
    }

}