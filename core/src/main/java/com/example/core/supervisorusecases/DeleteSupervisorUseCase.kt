package com.example.core.supervisorusecases

import android.util.Log
import com.example.core.di.EventCollection
import com.example.core.di.SupervisorCollection
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class DeleteSupervisorUseCase @Inject constructor(
    @SupervisorCollection
    private val booksRef: CollectionReference
) {
    fun execute(supervisorId: String) {
        booksRef.document(supervisorId).delete()
    }
}