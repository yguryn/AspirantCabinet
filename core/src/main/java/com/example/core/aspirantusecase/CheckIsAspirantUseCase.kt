package com.example.core.aspirantusecase

import com.example.core.di.AspirantCollection
import com.example.core.model.Aspirant
import com.example.core.utils.Constants.EMAIL
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class CheckIsAspirantUseCase @Inject constructor(
    @AspirantCollection
    private val aspirantRef: CollectionReference,
) {
    suspend fun execute(email: String): Aspirant? {
        val deferred = CompletableDeferred<Aspirant?>()
        aspirantRef.whereEqualTo(EMAIL, email).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    val aspirant = querySnapshot.documents[0].toObject(Aspirant::class.java)
                    aspirant?.id = querySnapshot.documents[0].id
                    deferred.complete(aspirant)
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