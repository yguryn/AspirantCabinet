package com.example.core.eventusecases

import com.example.core.di.EventCollection
import com.example.core.model.Event
import com.google.firebase.firestore.CollectionReference
import java.util.*
import javax.inject.Inject

class GetEventsForDateUseCase @Inject constructor(
    @EventCollection
    private val eventRef: CollectionReference,
) {
    fun execute(userId: String, date: Date, callback: (List<Event>) -> Unit) {
        val startCalendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val endCalendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        eventRef
            .whereEqualTo("user_id", userId)
            .whereGreaterThanOrEqualTo("event_start", startCalendar.time)
            .whereLessThanOrEqualTo("event_start", endCalendar.time)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val events = querySnapshot.documents.mapNotNull { it.toObject(Event::class.java) }
                callback(events)
            }
    }
}