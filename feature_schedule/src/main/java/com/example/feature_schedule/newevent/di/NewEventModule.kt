package com.example.feature_schedule.newevent.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_schedule.newevent.NewEventViewModel
import com.example.feature_schedule.schedule.ScheduleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class NewEventModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewEventViewModel::class)
    abstract fun bindHeroViewModel(viewModel: NewEventViewModel): ViewModel
}