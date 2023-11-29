package com.example.feature_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.administratorusecase.CheckIsAdministratorUseCase
import com.example.core.aspirantusecase.CheckIsAspirantUseCase
import com.example.core.supervisorusecases.CheckIsSupervisorUseCase
import com.example.core.utils.Constants.ADMINISTRATOR
import com.example.core.utils.Constants.ASPIRANT
import com.example.core.utils.Constants.FCM_TOKEN
import com.example.core.utils.Constants.RESEARCH_ID
import com.example.core.utils.Constants.SUPERVISOR
import com.example.core.utils.Constants.USER_ID
import com.example.core.utils.Constants.USER_TYPE
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val checkIsAspirantUseCase: CheckIsAspirantUseCase,
    private val checkIsSupervisorUseCase: CheckIsSupervisorUseCase,
    private val checkIsAdministratorUseCase: CheckIsAdministratorUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
) : ViewModel() {

    private var _usertype = MutableLiveData<UserType>()
    val userType: LiveData<UserType>
        get() = _usertype

    suspend fun determineUserType(email: String) {
        val aspirant = checkIsAspirantUseCase.execute(email)
        aspirant?.let {
            writeUserInfo(aspirant.id, ASPIRANT)
            writeResearch(aspirant.researchId)
            _usertype.postValue(UserType.ASPIRANT)
            return@let
        }
        val supervisor = checkIsSupervisorUseCase.execute(email)
        supervisor?.let {
            writeUserInfo(supervisor.id, SUPERVISOR)
            _usertype.postValue(UserType.SUPERVISOR)
            return@let
        }
        val administrator = checkIsAdministratorUseCase.execute(email)
        administrator?.let {
            writeUserInfo(administrator.id, ADMINISTRATOR)
            _usertype.postValue(UserType.ADMINISTRATOR)
            return@let
        }
    }

    private fun writeUserInfo(id: String, type: String) {
        sharedPreferencesHelper.putString(USER_ID, id)
        sharedPreferencesHelper.putString(USER_TYPE, type)
        val token = sharedPreferencesHelper.getString(FCM_TOKEN)
        if (token != null) {
            if (type == ASPIRANT) {
                val userRef = FirebaseFirestore.getInstance().document("aspirant/$id")
                userRef.update("fcmToken", token)
            } else if (type == SUPERVISOR) {
                val userRef = FirebaseFirestore.getInstance().document("supervisor/$id")
                userRef.update("fcmToken", token)
            }
            val userRef = FirebaseFirestore.getInstance().document("$type/$id")
            userRef.update("fcmToken", token)
        }
    }

    private fun writeResearch(researchId: String) {
        sharedPreferencesHelper.putString(RESEARCH_ID, researchId)
    }

    fun getUserInfo() = sharedPreferencesHelper.getString(USER_TYPE)
}
