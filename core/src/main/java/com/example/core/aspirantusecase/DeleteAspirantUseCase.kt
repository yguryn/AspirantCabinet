package com.example.core.aspirantusecase

import android.util.Log
import com.example.core.di.AspirantCollection
import com.example.core.di.EventCollection
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class DeleteAspirantUseCase @Inject constructor(
    @AspirantCollection
    private val booksRef: CollectionReference
) {
    fun execute(aspirantId: String) {
        booksRef.document(aspirantId).delete()
    }
}