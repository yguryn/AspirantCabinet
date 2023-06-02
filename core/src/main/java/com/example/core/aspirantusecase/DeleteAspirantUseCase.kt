package com.example.core.aspirantusecase

import com.example.core.di.AspirantCollection
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class DeleteAspirantUseCase @Inject constructor(
    @AspirantCollection
    private val aspirantRef: CollectionReference
) {
    fun execute(aspirantId: String) {
        aspirantRef.document(aspirantId).delete()
    }
}