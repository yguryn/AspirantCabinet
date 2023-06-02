package com.example.di

import com.example.core.di.CoreComponent
import com.example.feature_login.LoginFragment
import com.example.feature_login.di.LoginModule
import dagger.Component

@Component(
    modules = [
        LoginModule::class,
    ],
    dependencies = [CoreComponent::class]
)
@FragmentScope
interface LoginComponent {

    fun inject(loginFragment: LoginFragment)

}