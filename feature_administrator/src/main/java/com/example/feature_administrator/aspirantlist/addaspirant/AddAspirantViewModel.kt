package com.example.feature_administrator.aspirantlist.addaspirant

import androidx.lifecycle.ViewModel
import com.example.core.aspirantusecase.AddAspirantUseCase
import com.example.core.aspirantusecase.GetAllAspirantsUseCase
import com.example.core.model.Aspirant
import javax.inject.Inject

class AddAspirantViewModel @Inject constructor(
    private val addAspirantUseCase: AddAspirantUseCase,
) : ViewModel() {

    fun addAspirant(aspirant: Aspirant) {
        addAspirantUseCase.execute(aspirant)
    }
}