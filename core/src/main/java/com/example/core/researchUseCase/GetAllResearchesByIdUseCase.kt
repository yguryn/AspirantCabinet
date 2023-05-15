package com.example.core.researchUseCase

import android.util.Log
import com.example.core.di.ResearchCollection
import com.example.core.model.Article
import com.example.core.model.Research
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetAllResearchesByIdUseCase @Inject constructor(
    @ResearchCollection
    private val researchesRef: CollectionReference
) {
    suspend fun execute(aspirantId: String): MutableList<Article> {
        val deferred = CompletableDeferred<MutableList<Article>>()
        researchesRef.document(aspirantId.trim()).get()
            .addOnSuccessListener { document ->
                val research = document.toObject(Research::class.java)
                val articles = research?.listOfArticles
                if (articles != null) {
                    deferred.complete(articles)
                }
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}