package com.example.core.eventusecases

import android.util.Log
import com.example.core.di.EventCollection
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    @EventCollection
    private val booksRef: CollectionReference
) {
    fun execute(eventId: String) {
        Log.d("TTT","event $eventId")
        booksRef.document(eventId).delete()
    }
}