package com.example.core.eventusecases

import com.example.core.di.EventCollection
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    @EventCollection
    private val eventRef: CollectionReference
) {
    fun execute(eventId: String) {
        eventRef.document(eventId).delete()
    }
}