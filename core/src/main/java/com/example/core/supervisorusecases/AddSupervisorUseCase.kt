package com.example.core.supervisorusecases

import com.example.core.di.SupervisorCollection
import com.example.core.model.Supervisor
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class AddSupervisorUseCase @Inject constructor(
    @SupervisorCollection
    private val supervisorRef: CollectionReference,
) {

    fun execute(supervisor: Supervisor) {
        supervisorRef.add(supervisor)
    }
}