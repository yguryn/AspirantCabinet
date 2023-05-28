package com.example.core.researchUseCase

import android.util.Log
import com.example.core.di.ResearchCollection
import com.example.core.di.SupervisorCollection
import com.example.core.model.Research
import com.example.core.model.Supervisor
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetResearchByIdUseCase @Inject constructor(
    @ResearchCollection
    private val booksRef: CollectionReference,
) {
    suspend fun execute(researchId: String): Research {
        val deferred = CompletableDeferred<Research>()
        Log.d("TTT","research $researchId")
        booksRef.document(researchId).get()
            .addOnSuccessListener {
                val research = it.toObject(Research::class.java)
                research?.id = it.id
                Log.d("TTT","research $research")

                if (research != null) {
                    deferred.complete(research)
                }
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}