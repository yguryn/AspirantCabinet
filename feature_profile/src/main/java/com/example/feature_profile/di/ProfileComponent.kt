package com.example.feature_profile.di

import com.example.core.di.CoreComponent
import com.example.feature_profile.ProfileFragment
import dagger.Component
import javax.inject.Scope


@Component(
    modules = [
        ProfileModule::class,
    ],
    dependencies = [CoreComponent::class]
)
@FragmentScope
interface ProfileComponent {
    fun inject(profileFragment: ProfileFragment)
}