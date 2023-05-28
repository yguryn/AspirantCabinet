package com.example.feature_administrator.modifyaspirant.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_administrator.aspirantlist.addsupervisor.AddSupervisorViewModel
import com.example.feature_administrator.modifyaspirant.ModifyAspirantViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class ModifyAspirantModule {

    @Binds
    @IntoMap
    @ViewModelKey(ModifyAspirantViewModel::class)
    abstract fun bindHeroViewModel(viewModel: ModifyAspirantViewModel): ViewModel
}