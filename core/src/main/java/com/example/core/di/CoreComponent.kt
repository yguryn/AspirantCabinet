package com.example.core.di

import android.content.Context
import com.example.core.eventusecases.DeleteEventUseCase
import com.example.core.eventusecases.GetAllEventsUseCase
import com.example.core.eventusecases.GetEventByIdUseCase
import com.example.core.eventusecases.ModifyEventUseCase
import com.example.core.researchUseCase.AddResearchUseCase
import com.example.core.researchUseCase.GetAllResearchesUseCase
import com.example.core.researchUseCase.UpdateResearchUseCase
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {
    fun appContext(): Context

    @EventCollection

    fun eventCollection(): CollectionReference

    @ResearchCollection

    fun researchCollection(): CollectionReference

    @AspirantCollection
    fun aspirantCollection(): CollectionReference

    @SupervisorCollection
    fun supervisorCollection(): CollectionReference

    fun getAllEventsUseCase(): GetAllEventsUseCase

    fun deleteEventUseCase(): DeleteEventUseCase

    fun getEventByIdUseCase(): GetEventByIdUseCase

    fun getAllResearchesUseCase(): GetAllResearchesUseCase

    fun addResearchUseCase(): AddResearchUseCase

    fun modifyEventUseCase(): ModifyEventUseCase

    fun updateResearchUseCase(): UpdateResearchUseCase

    fun sharedPrefHelper(): SharedPreferencesHelper
}