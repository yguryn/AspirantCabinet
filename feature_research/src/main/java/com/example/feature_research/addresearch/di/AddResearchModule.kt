package com.example.feature_research.addresearch.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_research.addresearch.AddResearchViewModel
import com.example.feature_research.research.ResearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class AddResearchModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddResearchViewModel::class)
    abstract fun bindHeroViewModel(viewModel: AddResearchViewModel): ViewModel
}