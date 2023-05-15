package com.example.core.aspirantusecase

import com.example.core.di.AspirantCollection
import com.example.core.di.EventCollection
import com.example.core.model.Aspirant
import com.example.core.model.Event
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class AddAspirantUseCase @Inject constructor(
    @AspirantCollection
    private val booksRef: CollectionReference
) {
    fun execute(aspirant: Aspirant) {
        booksRef.add(aspirant)
    }
}