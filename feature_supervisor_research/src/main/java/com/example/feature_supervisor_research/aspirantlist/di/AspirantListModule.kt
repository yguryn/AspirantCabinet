package com.example.feature_supervisor_research.aspirantlist.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_supervisor_research.aspirantlist.AspirantListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class AspirantListModule {
    @Binds
    @IntoMap
    @ViewModelKey(AspirantListViewModel::class)
    abstract fun bindHeroViewModel(viewModel: AspirantListViewModel): ViewModel
}