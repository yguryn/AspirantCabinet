package com.example.feature_administrator.aspirantlist.addaspirant.di

import androidx.lifecycle.ViewModel
import com.example.core.di.viewmodel.ViewModelBuilderModule
import com.example.core.di.viewmodel.ViewModelKey
import com.example.feature_administrator.aspirantlist.addaspirant.AddAspirantViewModel
import com.example.feature_administrator.aspirantlist.listOfAspirants.AspirantListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class AddAspirantModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddAspirantViewModel::class)
    abstract fun bindHeroViewModel(viewModel: AddAspirantViewModel): ViewModel
}