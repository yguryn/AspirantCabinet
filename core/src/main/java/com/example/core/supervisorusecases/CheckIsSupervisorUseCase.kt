package com.example.core.supervisorusecases

import com.example.core.di.SupervisorCollection
import com.example.core.model.Supervisor
import com.example.core.utils.Constants.EMAIL
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class CheckIsSupervisorUseCase @Inject constructor(
    @SupervisorCollection
    private val supervisorRef: CollectionReference,
) {
    suspend fun execute(email: String): Supervisor? {
        val deferred = CompletableDeferred<Supervisor?>()
        supervisorRef.whereEqualTo(EMAIL, email.trim()).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    val supervisor = querySnapshot.documents[0].toObject(Supervisor::class.java)
                    supervisor?.id = querySnapshot.documents[0].id
                    deferred.complete(supervisor)
                } else {
                    deferred.complete(null)
                }
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}