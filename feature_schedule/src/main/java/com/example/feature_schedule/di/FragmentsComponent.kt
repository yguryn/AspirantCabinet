package com.example.feature_schedule.di

import com.example.core.di.CoreComponent
import com.example.feature_schedule.modifyevent.ModifyEventFragment
import com.example.feature_schedule.modifyevent.ModifyEventViewModel
import com.example.feature_schedule.modifyevent.di.ModifyEventModule
import com.example.feature_schedule.newevent.NewEventFragment
import com.example.feature_schedule.newevent.di.NewEventModule
import com.example.feature_schedule.schedule.ScheduleFragment
import com.example.feature_schedule.schedule.di.ScheduleModule
import dagger.Component

@Component(
    modules = [
        ScheduleModule::class,
        NewEventModule::class,
        ModifyEventModule::class
    ],
    dependencies = [CoreComponent::class]
)
@FragmentScope
interface FragmentsComponent {

    fun inject(scheduleFragment: ScheduleFragment)

    fun inject(newEventFragment: NewEventFragment)

    fun inject(modifyEventFragment: ModifyEventFragment)

}