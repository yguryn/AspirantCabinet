package com.example.core.researchUseCase

import com.example.core.di.ResearchCollection
import com.example.core.model.Task
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class UpdateTasksUseCase @Inject constructor(
    @ResearchCollection
    private val researchRef: CollectionReference,
) {
    fun execute(listOfTasks: List<Task>, researchId: String) {
        val documentRef = researchRef.document(researchId.trim())

        val data = hashMapOf<String, Any>()
        data["listOfTasks"] = listOfTasks

        documentRef.update(data)
    }
}