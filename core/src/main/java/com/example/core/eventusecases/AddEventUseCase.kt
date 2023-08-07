package com.example.core.eventusecases

import android.util.Log
import com.example.core.di.EventCollection
import com.example.core.model.Event
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    @EventCollection
    private val eventRef: CollectionReference
) {
    fun execute(event: Event) {
        Log.d("TTT","$event")
        eventRef.add(event)
    }
}