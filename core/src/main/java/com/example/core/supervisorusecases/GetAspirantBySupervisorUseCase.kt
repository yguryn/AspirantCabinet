package com.example.core.supervisorusecases

import com.example.core.di.AspirantCollection
import com.example.core.di.SupervisorCollection
import com.example.core.model.Aspirant
import com.example.core.model.Supervisor
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

class GetAspirantsBySupervisorUseCase @Inject constructor(
    @SupervisorCollection
    private val supervisorRef: CollectionReference,
    @AspirantCollection
    private val aspirantRef: CollectionReference,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {
    suspend fun execute(): MutableList<Aspirant> {
        val supervisor = getSupervisor()
        val deferredList = mutableListOf<Deferred<Aspirant?>>()

        supervisor.listOfAspirants.forEach { aspirantId ->
            val deferredAspirant = CompletableDeferred<Aspirant?>()
            deferredList.add(deferredAspirant)

            aspirantRef.document(aspirantId.trim()).get()
                .addOnSuccessListener { asp ->
                    val aspirant = asp.toObject(Aspirant::class.java)
                    aspirant?.id = aspirantId
                    deferredAspirant.complete(aspirant)
                }
        }

        val aspirants = deferredList.awaitAll().filterNotNull().toMutableList()
        return aspirants
    }

    suspend fun getSupervisor(): Supervisor {
        val supervisorId = sharedPreferencesHelper.getString("USER_ID")!!
        val deferredSupervisor = CompletableDeferred<Supervisor>()
        supervisorRef.document(supervisorId).get().addOnSuccessListener {
            deferredSupervisor.complete(it.toObject(Supervisor::class.java)!!)
        }
        return deferredSupervisor.await()
    }
}