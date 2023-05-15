package com.example.feature_administrator.aspirantlist.addsupervisor

import androidx.lifecycle.ViewModel
import com.example.core.aspirantusecase.AddAspirantUseCase
import com.example.core.model.Supervisor
import com.example.core.supervisorusecases.AddSupervisorUseCase
import javax.inject.Inject

class AddSupervisorViewModel @Inject constructor(
    private val addSupervisorUseCase: AddSupervisorUseCase,
) : ViewModel() {

    fun addSupervisor(supervisor: Supervisor) {
        addSupervisorUseCase.execute(supervisor)
    }
}