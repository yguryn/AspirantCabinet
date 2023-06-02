package com.example.core.supervisorusecases

import com.example.core.di.SupervisorCollection
import com.example.core.model.Supervisor
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class ModifySupervisorUseCase @Inject constructor(
    @SupervisorCollection
    private val supervisorRef: CollectionReference,
) {

    fun execute(supervisor: Supervisor) {
        val data = mapOf(
            "name" to supervisor.name,
            "surname" to supervisor.surname,
            "middleName" to supervisor.middleName,
            "phone" to supervisor.phone,
            "email" to supervisor.email,
            "faculty" to supervisor.faculty,
        )
        supervisorRef.document(supervisor.id).update(data)
    }
}