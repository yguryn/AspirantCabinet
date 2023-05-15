package com.example.core.aspirantusecase

import android.util.Log
import com.example.core.di.AspirantCollection
import com.example.core.di.EventCollection
import com.example.core.model.Aspirant
import com.example.core.model.Event
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetAllAspirantsUseCase @Inject constructor(
    @AspirantCollection
    private val booksRef: CollectionReference,
) {

    suspend fun execute(): MutableList<Aspirant> {
        val deferred = CompletableDeferred<MutableList<Aspirant>>()

        booksRef.get()
            .addOnSuccessListener { documents ->
                val events = mutableListOf<Aspirant>()
                for (document in documents) {
                    val event = document.toObject(Aspirant::class.java)
                    event.id = document.id
                    events.add(event)
                    Log.d("TTT","hyi$event")
                }
                deferred.complete(events)
            }
            .addOnFailureListener { exception ->
                Log.d("TTT","fireError")
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}