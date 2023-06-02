package com.example.core.eventusecases

import com.example.core.di.EventCollection
import com.example.core.model.Event
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    @EventCollection
    private val eventRef: CollectionReference,
) {
    fun execute(documentId: String, callback: (event: Event) -> Unit) {
        var event: Event
        eventRef.document(documentId).get().addOnSuccessListener {
            event = it.toObject(Event::class.java)!!
            callback(event)
        }
    }
}