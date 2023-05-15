package com.example.core.supervisorusecases

import android.util.Log
import com.example.core.di.AspirantCollection
import com.example.core.di.SupervisorCollection
import com.example.core.model.Supervisor
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class CheckIsSupervisorUseCase @Inject constructor(
    @SupervisorCollection
    private val booksRef: CollectionReference,
) {
    suspend fun execute(email: String): Supervisor {
        val deferred = CompletableDeferred<Supervisor>()
        Log.d("TTT", "$email")
        booksRef.whereEqualTo("email", email.trim()).get()
            .addOnSuccessListener { querySnapshot ->
                if(querySnapshot.documents.isNotEmpty()) {
                    val supervisor = querySnapshot.documents[0].toObject(Supervisor::class.java)
                    supervisor?.id = querySnapshot.documents[0].id

                    Log.d("TTT", "$supervisor")
                    if (supervisor != null) {
                        deferred.complete(supervisor)
                    }
                }
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}