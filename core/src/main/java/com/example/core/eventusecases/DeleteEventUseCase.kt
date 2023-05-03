package com.example.core.eventusecases

import com.example.core.di.EventCollection
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    @EventCollection
    private val booksRef: CollectionReference
) {
    fun execute() {
        booksRef.document("wuAxgTrJlJidKlGJgGmT").delete()
    }
}