package com.example.feature_research.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelFactory
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_research.research.ResearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class ResearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(ResearchViewModel::class)
    abstract fun bindHeroViewModel(viewModel: ResearchViewModel): ViewModel
}