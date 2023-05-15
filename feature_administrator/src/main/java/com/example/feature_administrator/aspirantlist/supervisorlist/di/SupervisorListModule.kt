package com.example.feature_administrator.aspirantlist.supervisorlist.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_administrator.aspirantlist.listOfAspirants.AspirantListViewModel
import com.example.feature_administrator.aspirantlist.supervisorlist.SupervisorListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [ViewModelBuilderModule::class])
abstract class SupervisorListModule {

    @Binds
    @IntoMap
    @ViewModelKey(SupervisorListViewModel::class)
    abstract fun bindHeroViewModel(viewModel: SupervisorListViewModel): ViewModel
}