package com.example.core.administratorusecase

import com.example.core.di.AdministratorCollection
import com.example.core.model.Administrator
import com.example.core.utils.Constants.EMAIL
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class CheckIsAdministratorUseCase @Inject constructor(
    @AdministratorCollection
    private val adminRef: CollectionReference,
) {

    suspend fun execute(email: String): Administrator? {
        val deferred = CompletableDeferred<Administrator?>()
        adminRef.whereEqualTo(EMAIL, email.trim()).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    val administrator =
                        querySnapshot.documents[0].toObject(Administrator::class.java)
                    administrator?.id = querySnapshot.documents[0].id
                    deferred.complete(administrator)
                } else {
                    deferred.complete(null)
                }
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}