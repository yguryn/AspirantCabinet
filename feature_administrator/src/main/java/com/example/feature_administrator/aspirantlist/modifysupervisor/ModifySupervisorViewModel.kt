package com.example.feature_administrator.aspirantlist.modifysupervisor

import androidx.lifecycle.ViewModel
import com.example.core.aspirantusecase.DeleteAspirantUseCase
import com.example.core.aspirantusecase.GetAspirantByIdUseCase
import com.example.core.aspirantusecase.ModifyAspirantUseCase
import com.example.core.model.Supervisor
import com.example.core.supervisorusecases.DeleteSupervisorUseCase
import com.example.core.supervisorusecases.GetAllSupervisorsByFaculty
import com.example.core.supervisorusecases.GetSupervisorByIdUseCase
import com.example.core.supervisorusecases.ModifySupervisorUseCase
import javax.inject.Inject

class ModifySupervisorViewModel @Inject constructor(
    private val modifySupervisorUseCase: ModifySupervisorUseCase,
    private val deleteSupervisorUseCase: DeleteSupervisorUseCase
) : ViewModel() {

    fun modifySupervisor(supervisor: Supervisor) {
        modifySupervisorUseCase.execute(supervisor)
    }

    fun deleteSupervisor(supervisorId: String) {
        deleteSupervisorUseCase.execute(supervisorId)
    }
}