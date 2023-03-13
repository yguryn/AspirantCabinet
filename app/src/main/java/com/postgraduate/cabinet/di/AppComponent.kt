package com.postgraduate.cabinet.di

import com.example.core.di.CoreComponent
import com.example.core.di.Screen
import com.postgraduate.cabinet.ApplicationComponent
import com.postgraduate.cabinet.MainActivity
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
@Screen
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(applicationComponent: ApplicationComponent)
}
