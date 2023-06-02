package com.example.feature_events_list.di

import com.example.core.di.CoreComponent
import com.example.feature_events_list.feature_event_list.EventListFragment
import com.example.feature_events_list.feature_event_list.di.EventListModule
import dagger.Component

@Component(
    modules = [
        EventListModule::class,
    ],
    dependencies = [CoreComponent::class]
)
@FragmentScope
interface EventListComponent {

    fun inject(eventListFragment: EventListFragment)

}