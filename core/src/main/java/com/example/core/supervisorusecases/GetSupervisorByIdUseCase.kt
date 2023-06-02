package com.example.core.supervisorusecases

import com.example.core.di.SupervisorCollection
import com.example.core.model.Supervisor
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetSupervisorByIdUseCase @Inject constructor(
    @SupervisorCollection
    private val supervisorRef: CollectionReference,
) {
    suspend fun execute(supervisorId: String): Supervisor {
        val deferred = CompletableDeferred<Supervisor>()
        supervisorRef.document(supervisorId).get()
            .addOnSuccessListener {
                    val supervisor = it.toObject(Supervisor::class.java)
                    supervisor?.id = it.id

                    if (supervisor != null) {
                        deferred.complete(supervisor)
                    }
                }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}