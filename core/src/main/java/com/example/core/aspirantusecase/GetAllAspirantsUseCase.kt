package com.example.core.aspirantusecase

import com.example.core.di.AspirantCollection
import com.example.core.model.Aspirant
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetAllAspirantsUseCase @Inject constructor(
    @AspirantCollection
    private val aspirantRef: CollectionReference,
) {
    suspend fun execute(): MutableList<Aspirant> {
        val deferred = CompletableDeferred<MutableList<Aspirant>>()

        aspirantRef.get()
            .addOnSuccessListener { documents ->
                val events = mutableListOf<Aspirant>()
                for (document in documents) {
                    val event = document.toObject(Aspirant::class.java)
                    event.id = document.id
                    events.add(event)
                }
                deferred.complete(events)
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}