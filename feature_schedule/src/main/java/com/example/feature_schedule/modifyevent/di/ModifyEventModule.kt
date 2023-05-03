package com.example.feature_schedule.modifyevent.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_schedule.modifyevent.ModifyEventViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class ModifyEventModule {

    @Binds
    @IntoMap
    @ViewModelKey(ModifyEventViewModel::class)
    abstract fun bindHeroViewModel(viewModel: ModifyEventViewModel): ViewModel
}