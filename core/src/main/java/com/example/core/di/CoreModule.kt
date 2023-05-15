package com.example.core.di

import android.app.Application
import android.content.Context
import com.example.core.utils.Constants.ADMINISTRATOR
import com.example.core.utils.Constants.ASPIRANT
import com.example.core.utils.Constants.EVENT
import com.example.core.utils.Constants.RESEARCH
import com.example.core.utils.Constants.SUPERVISOR
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule(val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    @EventCollection
    fun provideEventCollection(): CollectionReference = Firebase.firestore.collection(EVENT)

    @Provides
    @Singleton
    @ResearchCollection
    fun provideResearchCollection(): CollectionReference = Firebase.firestore.collection(RESEARCH)

    @Provides
    @Singleton
    @AspirantCollection
    fun provideAspirantCollection(): CollectionReference = Firebase.firestore.collection(ASPIRANT)

    @Provides
    @Singleton
    @SupervisorCollection
    fun provideSupervisorCollection(): CollectionReference = Firebase.firestore.collection(
        SUPERVISOR
    )

    @Provides
    @Singleton
    @AdministratorCollection
    fun provideAdministratorCollection(): CollectionReference = Firebase.firestore.collection(
        ADMINISTRATOR
    )

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(context: Context): SharedPreferencesHelper =
        SharedPreferencesHelper(context)
}
