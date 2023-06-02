package com.example.core.supervisorusecases

import com.example.core.di.SupervisorCollection
import com.example.core.model.Supervisor
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetAllSupervisorsByFaculty @Inject constructor(
    @SupervisorCollection
    private val supervisorRef: CollectionReference,
) {

    suspend fun execute(): MutableList<Supervisor> {
        val deferred = CompletableDeferred<MutableList<Supervisor>>()
        supervisorRef.get()
            .addOnSuccessListener { documents ->
                val supervisors = mutableListOf<Supervisor>()
                for (document in documents) {
                    val supervisor = document.toObject(Supervisor::class.java)
                    supervisor.id = document.id
                    supervisors.add(supervisor)
                }
                deferred.complete(supervisors)
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}