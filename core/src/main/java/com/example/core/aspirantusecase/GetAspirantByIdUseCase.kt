package com.example.core.aspirantusecase

import com.example.core.di.AspirantCollection
import com.example.core.model.Aspirant
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetAspirantByIdUseCase @Inject constructor(
    @AspirantCollection
    private val aspirantRef: CollectionReference,
) {
    suspend fun execute(id: String): Aspirant {
        val deferred = CompletableDeferred<Aspirant>()

        aspirantRef.document(id).get()
            .addOnSuccessListener { document ->
                val aspirant = document.toObject(Aspirant::class.java)
                aspirant?.id = document.id
                if (aspirant != null) {
                    deferred.complete(aspirant)
                }
            }

            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}