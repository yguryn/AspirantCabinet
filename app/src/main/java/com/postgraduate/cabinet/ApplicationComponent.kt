package com.postgraduate.cabinet

import android.app.Application
import com.example.core.di.CoreComponent
import com.example.core.di.CoreComponentProvider
import com.example.core.di.CoreModule
import com.example.core.di.DaggerCoreComponent

class ApplicationComponent : Application(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        initCoreComponent()
    }

    private fun initCoreComponent() {
        coreComponent = DaggerCoreComponent
            .builder().coreModule(CoreModule(this)).build()
    }

    override fun provideCoreComponent(): CoreComponent {
        if (!this::coreComponent.isInitialized) {
            initCoreComponent()
        }
        return coreComponent
    }
}
