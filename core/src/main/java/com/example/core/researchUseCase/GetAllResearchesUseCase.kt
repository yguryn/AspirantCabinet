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
    suspend fun execute(): MutableList<Research> {
        val deferred = CompletableDeferred<MutableList<Research>>()
        val userId = sharedPreferencesHelper.getString("USER_ID")
        researchesRef.whereEqualTo("user_id",userId).get()
            .addOnSuccessListener { documents ->
                val researches = mutableListOf<Research>()
                for (document in documents) {
                    val research = document.toObject(Research::class.java)
                    research.id = document.id
                    researches.add(research)
                }
                deferred.complete(researches)
                Log.d("TTT","RES$researches")
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}