package com.example.core.supervisorusecases

import com.example.core.di.SupervisorCollection
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class DeleteSupervisorUseCase @Inject constructor(
    @SupervisorCollection
    private val supervisorRef: CollectionReference
) {
    fun execute(supervisorId: String) {
        supervisorRef.document(supervisorId).delete()
    }
}