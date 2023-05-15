package com.example.core.administratorusecase

import android.util.Log
import com.example.core.di.AdministratorCollection
import com.example.core.di.SupervisorCollection
import com.example.core.model.Administrator
import com.example.core.model.Supervisor
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class CheckIsAdministratorUseCase @Inject constructor(
    @AdministratorCollection
    private val booksRef: CollectionReference,
) {

    suspend fun execute(email: String): Administrator {
        val deferred = CompletableDeferred<Administrator>()
        Log.d("TTT", "$email")
        booksRef.whereEqualTo("email", email.trim()).get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("TTT", "${querySnapshot.documents.isNotEmpty()}")

                if (querySnapshot.documents.isNotEmpty()) {
                    val administrator = querySnapshot.documents[0].toObject(Administrator::class.java)
                    administrator?.id = querySnapshot.documents[0].id

                    Log.d("TTT", "$administrator")
                    if (administrator != null) {
                        deferred.complete(administrator)
                    }
                }
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}