package com.example.core.researchUseCase

import com.example.core.di.ResearchCollection
import com.example.core.model.Research
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    @ResearchCollection
    private val researchesRef: CollectionReference,
) {
    fun execute(research: Research) {
        val data = mapOf(
            "user_id" to research.userId,
            "object_research" to research.objectResearch,
            "subject_research" to research.subjectResearch,
            "listOfArticles" to research.listOfArticles,
        )
        researchesRef.document(research.id).update(data)
    }
}