package com.example.feature_profile.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class ProfileModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindHeroViewModel(viewModel: ProfileViewModel): ViewModel
}
