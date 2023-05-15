package com.example.feature_login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.administratorusecase.CheckIsAdministratorUseCase
import com.example.core.aspirantusecase.GetTypeOfUserUseCase
import com.example.core.eventusecases.GetAllEventsUseCase
import com.example.core.model.Administrator
import com.example.core.model.Aspirant
import com.example.core.model.Event
import com.example.core.model.Supervisor
import com.example.core.supervisorusecases.CheckIsSupervisorUseCase
import com.example.core.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class LoginViewModel  @Inject constructor(
    private val getTypeOfUserUseCase: GetTypeOfUserUseCase,
    private val checkIsSupervisorUseCase: CheckIsSupervisorUseCase,
    private val checkIsAdministratorUseCase: CheckIsAdministratorUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private var _aspirant = MutableLiveData<Aspirant>()
    val aspirant: LiveData<Aspirant>
        get() = _aspirant

    private var _supervisor = MutableLiveData<Supervisor>()
    val supervisor: LiveData<Supervisor>
        get() = _supervisor

    private var _administrator = MutableLiveData<Administrator>()
    val administrator: LiveData<Administrator>
        get() = _administrator

    fun checkAspirant(email: String) {
        Log.d("TTT","check")
        viewModelScope.launch {
            _administrator.value = checkIsAdministratorUseCase.execute(email)
            _aspirant.value = getTypeOfUserUseCase.execute(email)
            _supervisor.value = checkIsSupervisorUseCase.execute(email)
        }
    }

    fun writeUserInfo(id: String, type: String) {
        sharedPreferencesHelper.putString("USER_ID",id)
        sharedPreferencesHelper.putString("USER_TYPE",type)
    }
}