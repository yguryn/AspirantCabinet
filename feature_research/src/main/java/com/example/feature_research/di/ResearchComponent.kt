package com.example.feature_research.di

import com.example.core.di.CoreComponent
import com.example.core.di.Screen
import com.example.feature_research.research.ResearchFragment
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [ResearchModule::class]
)
@Screen
interface ResearchComponent {
    fun inject(fragment: ResearchFragment)
}