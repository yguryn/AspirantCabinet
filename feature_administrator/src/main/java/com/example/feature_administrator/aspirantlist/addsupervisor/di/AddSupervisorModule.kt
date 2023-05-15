package com.example.feature_administrator.aspirantlist.addsupervisor.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_administrator.aspirantlist.addaspirant.AddAspirantViewModel
import com.example.feature_administrator.aspirantlist.addsupervisor.AddSupervisorViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [ViewModelBuilderModule::class])
abstract class AddSupervisorModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddSupervisorViewModel::class)
    abstract fun bindHeroViewModel(viewModel: AddSupervisorViewModel): ViewModel
}
