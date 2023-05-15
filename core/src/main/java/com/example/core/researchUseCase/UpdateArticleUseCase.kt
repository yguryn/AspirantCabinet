package com.example.core.researchUseCase

import com.example.core.di.ResearchCollection
import com.example.core.model.Article
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class UpdateArticleUseCase @Inject constructor(
    @ResearchCollection
    private val researchesRef: CollectionReference,
) {
    fun execute(listOfArticles: List<Article>, researchId: String) {

        val data = mapOf(
            "listOfArticles" to listOfArticles,
        )
        researchesRef.document(researchId).update(data)
    }
}