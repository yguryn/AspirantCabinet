package com.example.core.eventusecases

import com.example.core.di.EventCollection
import com.example.core.model.Event
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    @EventCollection
    private val booksRef: CollectionReference
) {
    fun execute(event: Event) {
        booksRef.add(event)
    }
}