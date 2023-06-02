package com.example.core.aspirantusecase

import com.example.core.di.AspirantCollection
import com.example.core.model.Aspirant
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetTypeOfUserUseCase @Inject constructor(
    @AspirantCollection
    private val aspirantRef: CollectionReference,
) {
    suspend fun execute(email: String): Aspirant {
        val deferred = CompletableDeferred<Aspirant>()

        aspirantRef.whereEqualTo("email", email).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    val aspirant = querySnapshot.documents[0].toObject(Aspirant::class.java)
                    aspirant?.id = querySnapshot.documents[0].id
                    if (aspirant != null) {
                        deferred.complete(aspirant)
                    }
                }
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}