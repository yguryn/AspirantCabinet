package com.example.core.supervisorusecases

import com.example.core.di.ResearchCollection
import com.example.core.di.SupervisorCollection
import com.example.core.model.Research
import com.example.core.model.Supervisor
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class AddSupervisorUseCase @Inject constructor(
    @SupervisorCollection
    private val researchesRef: CollectionReference,
) {

    fun execute(supervisor: Supervisor) {
        researchesRef.add(supervisor)
    }
}