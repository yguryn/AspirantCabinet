package com.example.feature_supervisor_research.indplan.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_supervisor_research.aspirantlist.AspirantListViewModel
import com.example.feature_supervisor_research.indplan.IndPlanViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap



@Module(includes = [ViewModelBuilderModule::class])
abstract class IndPlanListModule {
    @Binds
    @IntoMap
    @ViewModelKey(IndPlanViewModel::class)
    abstract fun bindHeroViewModel(viewModel: IndPlanViewModel): ViewModel
}