package com.example.core.eventusecases

import android.util.Log
import com.example.core.di.EventCollection
import com.example.core.model.Event
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    @EventCollection
    private val eventRef: CollectionReference,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {

    suspend fun execute(): MutableList<Event> {
        val deferred = CompletableDeferred<MutableList<Event>>()
        val userId = sharedPreferencesHelper.getString("USER_ID")
        eventRef.whereEqualTo("user_id",userId?.trim()).get()
            .addOnSuccessListener { documents ->
                val events = mutableListOf<Event>()
                for (document in documents) {
                    val event = document.toObject(Event::class.java)
                    event.id = document.id
                    events.add(event)
                }
                deferred.complete(events)
                Log.d("TTT","curEvents$events")
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}