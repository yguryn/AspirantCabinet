package com.example.feature_supervisor_research.addnewtask.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_supervisor_research.addnewtask.AddNewTaskViewModel
import com.example.feature_supervisor_research.aspirantdetails.AspirantDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class AddNewTaskModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddNewTaskViewModel::class)
    abstract fun bindHeroViewModel(viewModel: AddNewTaskViewModel): ViewModel
}