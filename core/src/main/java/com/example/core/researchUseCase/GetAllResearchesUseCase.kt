package com.example.core.researchUseCase

import android.util.Log
import com.example.core.di.EventCollection
import com.example.core.di.ResearchCollection
import com.example.core.model.Research
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetAllResearchesUseCase @Inject constructor(
    @ResearchCollection
    private val researchesRef: CollectionReference,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {
    suspend fun execute(): Research {
        val deferred = CompletableDeferred<Research>()
        val researchId = sharedPreferencesHelper.getString("RESEARCH_ID")!!
        researchesRef.document(researchId).get()
            .addOnSuccessListener { document ->
                val research = document.toObject(Research::class.java)
                if (research != null) {
                    research.id = document.id
                }
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