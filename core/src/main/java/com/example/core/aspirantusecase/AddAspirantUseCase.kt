package com.example.core.aspirantusecase

import com.example.core.di.AspirantCollection
import com.example.core.model.Aspirant
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class AddAspirantUseCase @Inject constructor(
    @AspirantCollection
    private val aspirantRef: CollectionReference
) {
    fun execute(aspirant: Aspirant) {
        aspirantRef.add(aspirant)
    }
}