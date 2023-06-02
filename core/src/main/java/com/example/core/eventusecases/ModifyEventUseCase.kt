package com.example.core.eventusecases

import com.example.core.di.EventCollection
import com.example.core.model.Event
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class ModifyEventUseCase @Inject constructor(
    @EventCollection
    private val booksRef: CollectionReference,
) {
    fun execute(event: Event) {
        val data = mapOf(
            "user_id" to event.user_id,
            "title" to event.title,
            "description" to event.description,
            "event_start" to event.event_start,
            "event_end" to event.event_end
        )
        booksRef.document(event.id).update(data)
    }
}