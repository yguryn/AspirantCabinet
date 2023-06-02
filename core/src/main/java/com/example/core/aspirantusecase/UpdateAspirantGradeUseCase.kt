package com.example.core.aspirantusecase

import android.util.Log
import com.example.core.di.AspirantCollection
import com.example.core.model.Aspirant
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class UpdateAspirantGradeUseCase @Inject constructor(
    @AspirantCollection
    private val aspirantsRef: CollectionReference,
) {

    fun execute(aspirant: Aspirant) {
        val documentRef = aspirantsRef.document(aspirant.id.trim())

        val data = hashMapOf<String, Any>()
        data["grade"] = aspirant.grade

        documentRef.update(data)
            .addOnSuccessListener {
                Log.d("TTT", "Grade updated successfully.")
            }
            .addOnFailureListener { e ->
                Log.e("TTT", "Error updating grade: $e")
            }
    }
}