package com.example.feature_supervisor_research.addnewtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Research
import com.example.core.model.Task
import com.example.core.researchUseCase.GetResearchByIdUseCase
import com.example.core.researchUseCase.UpdateTasksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddNewTaskViewModel @Inject constructor(
    private val getResearchByIdUseCase: GetResearchByIdUseCase,
    private val updateTasksUseCase: UpdateTasksUseCase
) : ViewModel() {

    private var _research = MutableLiveData<Research>()
    val research: LiveData<Research>
        get() = _research

    fun getResearchByIdUseCase(researchId: String) {
        viewModelScope.launch {
            _research.value = getResearchByIdUseCase.execute(researchId)
        }
    }

    fun updateTasksUseCase(listOfTasks: List<Task>, researchId: String) {
        updateTasksUseCase.execute(listOfTasks, researchId)
    }
}