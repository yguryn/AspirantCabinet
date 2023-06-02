package com.example.feature_supervisor_research.di

import com.example.core.di.CoreComponent
import com.example.feature_supervisor_research.addnewtask.AddNewTaskFragment
import com.example.feature_supervisor_research.addnewtask.di.AddNewTaskModule
import com.example.feature_supervisor_research.aspirantdetails.AspirantDetailsFragment
import com.example.feature_supervisor_research.aspirantdetails.di.AspirantDetailsModule
import com.example.feature_supervisor_research.aspirantlist.AspirantListFragment
import com.example.feature_supervisor_research.aspirantlist.di.AspirantListModule
import dagger.Component

@Component(
    modules = [
        AspirantListModule::class,
        AspirantDetailsModule::class,
        AddNewTaskModule::class
    ],
    dependencies = [CoreComponent::class]
)
@FragmentScope
interface FragmentsComponent {

    fun inject(aspirantListFragment: AspirantListFragment)

    fun inject(aspirantDetailsFragment: AspirantDetailsFragment)

    fun inject(addNewTaskFragment: AddNewTaskFragment)
}