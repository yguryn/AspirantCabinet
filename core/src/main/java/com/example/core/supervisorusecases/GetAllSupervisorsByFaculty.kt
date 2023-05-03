package com.example.core.supervisorusecases

import android.util.Log
import com.example.core.di.SupervisorCollection
import com.example.core.model.Supervisor
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetAllSupervisorsByFaculty  @Inject constructor(
        @SupervisorCollection
        private val supervisorRef: CollectionReference,
        private val sharedPreferencesHelper: SharedPreferencesHelper
    ) {

    suspend fun execute(): MutableList<Supervisor> {
        val deferred = CompletableDeferred<MutableList<Supervisor>>()
        val userId = sharedPreferencesHelper.getString("FACULTY_ID")
        supervisorRef.whereEqualTo("faculty", "ІАТЕ").get()
            .addOnSuccessListener { documents ->
                val supervisors = mutableListOf<Supervisor>()
                for (document in documents) {
                    val supervisor = document.toObject(Supervisor::class.java)
                    supervisor.id = document.id
                    supervisors.add(supervisor)
                }
                deferred.complete(supervisors)
                Log.d("TTT","RES$supervisors")
            }
            .addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        return deferred.await()
    }
}