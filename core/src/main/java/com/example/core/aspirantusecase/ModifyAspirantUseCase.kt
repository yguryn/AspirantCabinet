package com.example.core.aspirantusecase

import com.example.core.di.AspirantCollection
import com.example.core.model.Aspirant
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class ModifyAspirantUseCase @Inject constructor(
    @AspirantCollection
    private val aspirantRef: CollectionReference,
) {
    fun execute(aspirant: Aspirant) {
        val data = mapOf(
            "name" to aspirant.name,
            "surname" to aspirant.surname,
            "middleName" to aspirant.middleName,
            "phone" to aspirant.phone,
            "email" to aspirant.email,
            "faculty" to aspirant.faculty,
            "isBudget" to aspirant.isBudget,
            "supervisorId" to aspirant.supervisorId,
            "group" to aspirant.group,
            "specialization" to aspirant.specialization,
        )
        aspirantRef.document(aspirant.id).update(data)
    }
}