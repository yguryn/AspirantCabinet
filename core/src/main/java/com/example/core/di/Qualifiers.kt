package com.example.core.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EventCollection

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ResearchCollection

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AspirantCollection

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SupervisorCollection