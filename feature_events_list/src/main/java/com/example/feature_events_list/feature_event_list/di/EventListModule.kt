package com.example.feature_events_list.feature_event_list.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_events_list.feature_event_list.EventListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [ViewModelBuilderModule::class])
abstract class EventListModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventListViewModel::class)
    abstract fun bindHeroViewModel(viewModel: EventListViewModel): ViewModel
}