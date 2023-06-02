package com.example.core.researchUseCase

import com.example.core.di.ResearchCollection
import com.example.core.model.Research
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetResearchByIdUseCase @Inject constructor(
    @ResearchCollection
    private val researchRef: CollectionReference,
) {
    suspend fun execute(researchId: String): Research {
        val deferred = CompletableDeferred<Research>()
        researchRef.document(researchId).get()
            .addOnSuccessListener {
                val research = it.toObject(Research::class.java)
                research?.id = it.id
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