package com.example.feature_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.aspirantusecase.GetAspirantByIdUseCase
import com.example.core.eventusecases.GetEventByIdUseCase
import com.example.core.eventusecases.ModifyEventUseCase
import com.example.core.model.Aspirant
import com.example.core.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val preferencesHelper: SharedPreferencesHelper,
    private val getAspirantByIdUseCase: GetAspirantByIdUseCase
) : ViewModel() {

    private var _aspirant = MutableLiveData<Aspirant>()
    val aspirant: LiveData<Aspirant>
        get() = _aspirant

    fun getUserInfo() {
        val userId = preferencesHelper.getString("USER_ID")
        val userType = preferencesHelper.getString("USER_TYPE")
        if (userType == "Aspirant") {
            viewModelScope.launch {
                _aspirant.value = getAspirantByIdUseCase.execute(userId!!)
            }
        }
    }
}