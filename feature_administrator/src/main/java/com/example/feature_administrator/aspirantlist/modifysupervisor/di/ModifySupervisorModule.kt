package com.example.feature_administrator.aspirantlist.modifysupervisor.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_administrator.aspirantlist.modifysupervisor.ModifySupervisorViewModel
import com.example.feature_administrator.aspirantlist.supervisorlist.SupervisorListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class ModifySupervisorModule {

    @Binds
    @IntoMap
    @ViewModelKey(ModifySupervisorViewModel::class)
    abstract fun bindHeroViewModel(viewModel: ModifySupervisorViewModel): ViewModel
}