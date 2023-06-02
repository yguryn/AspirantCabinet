package com.example.core.researchUseCase

import android.util.Log
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
            .addOnSuccessListener {
                Log.d("TTT", "List of tasks updated successfully.")
            }
            .addOnFailureListener { e ->
                Log.e("TTT", "Error updating list of tasks: $e")
            }
    }
}