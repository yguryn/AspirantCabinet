package com.example.feature_supervisor_research.aspirantdetails.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_supervisor_research.aspirantdetails.AspirantDetailsViewModel
import com.example.feature_supervisor_research.aspirantlist.AspirantListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class AspirantDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AspirantDetailsViewModel::class)
    abstract fun bindHeroViewModel(viewModel: AspirantDetailsViewModel): ViewModel
}