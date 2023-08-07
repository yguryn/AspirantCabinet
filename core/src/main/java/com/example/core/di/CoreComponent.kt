package com.example.core.di

import android.content.Context
import com.example.core.administratorusecase.CheckIsAdministratorUseCase
import com.example.core.aspirantusecase.*
import com.example.core.eventusecases.*
import com.example.core.researchUseCase.*
import com.example.core.supervisorusecases.*
import com.example.core.utils.ResourceManager
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

    @AdministratorCollection
    fun administratorCollection(): CollectionReference

    fun getAllEventsUseCase(): GetAllEventsUseCase

    fun deleteEventUseCase(): DeleteEventUseCase

    fun getEventByIdUseCase(): GetEventByIdUseCase

    fun getAllResearchesUseCase(): GetAllResearchesUseCase

    fun getAllAspirantsUseCase(): GetAllAspirantsUseCase

    fun addAspirantUseCase(): AddAspirantUseCase

    fun addSupervisorUseCase(): AddSupervisorUseCase

    fun getTypeOfUserUseCase(): GetTypeOfUserUseCase

    fun checkIsSupervisorUseCase(): CheckIsSupervisorUseCase

    fun checkIsAdministratorUseCase(): CheckIsAdministratorUseCase

    fun getAspirantByIdUseCase(): GetAspirantByIdUseCase

    fun addResearchUseCase(): AddResearchUseCase

    fun getAllResearchesByIdUseCase(): GetAllResearchesByIdUseCase

    fun getSuperVisorByIdUseCase() : GetSupervisorByIdUseCase

    fun getResearchByIdUseCase() : GetResearchByIdUseCase

    fun modifyEventUseCase(): ModifyEventUseCase

    fun modifyAspirantUseCase(): ModifyAspirantUseCase

    fun deleteAspirantUseCase(): DeleteAspirantUseCase

    fun updateAspirantGradeUseCase(): UpdateAspirantGradeUseCase

    fun updateTaskUseCase(): UpdateTasksUseCase

    fun updateResearchUseCase(): UpdateResearchUseCase

    fun modifySupervisorUseCase(): ModifySupervisorUseCase

    fun deleteSupervisorUseCase(): DeleteSupervisorUseCase

    fun getEventsForDateUseCase(): GetEventsForDateUseCase

    fun sharedPrefHelper(): SharedPreferencesHelper

    fun resourceManager(): ResourceManager
}