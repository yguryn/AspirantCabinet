package com.example.core.supervisorusecases

import android.util.Log
import com.example.core.di.SupervisorCollection
import com.example.core.model.Supervisor
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetSupervisorByIdUseCase @Inject constructor(
    @SupervisorCollection
    private val booksRef: CollectionReference,
) {
    suspend fun execute(supervisorId: String): Supervisor {
        val deferred = CompletableDeferred<Supervisor>()
        booksRef.document(supervisorId).get()
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