package com.example.core.eventusecases

import android.util.Log
import com.example.core.di.EventCollection
import com.example.core.model.Event
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    @EventCollection
    private val booksRef: CollectionReference,
) {
    fun execute(documentId: String, callback: (event: Event) -> Unit) {
        var event: Event
        booksRef.document(documentId).get().addOnSuccessListener {
            event = it.toObject(Event::class.java)!!
            callback(event)
            Log.d("TTT", "evf ${event}")
        }
    }
}