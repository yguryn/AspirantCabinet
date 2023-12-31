package com.example.feature_administrator.aspirantlist.di

import com.example.core.di.CoreComponent
import com.example.core.di.Screen
import com.example.feature_administrator.aspirantlist.addaspirant.AddAspirantFragment
import com.example.feature_administrator.aspirantlist.addaspirant.AddAspirantSecondStepFragment
import com.example.feature_administrator.aspirantlist.addaspirant.di.AddAspirantModule
import com.example.feature_administrator.aspirantlist.addsupervisor.AddSupervisorFragment
import com.example.feature_administrator.aspirantlist.addsupervisor.di.AddSupervisorModule
import com.example.feature_administrator.aspirantlist.listOfAspirants.AspirantListFragment
import com.example.feature_administrator.aspirantlist.listOfAspirants.di.AspirantListModule
import com.example.feature_administrator.aspirantlist.modifysupervisor.ModifySupervisorFragment
import com.example.feature_administrator.aspirantlist.modifysupervisor.di.ModifySupervisorModule
import com.example.feature_administrator.aspirantlist.supervisorlist.SupervisorListFragment
import com.example.feature_administrator.aspirantlist.supervisorlist.di.SupervisorListModule
import com.example.feature_administrator.modifyaspirant.ModifyAspirantFragment
import com.example.feature_administrator.modifyaspirant.di.ModifyAspirantModule
import dagger.Component

@Component(
    modules = [AspirantListModule::class,
        AddAspirantModule::class,
        SupervisorListModule::class,
        AddSupervisorModule::class,
        ModifyAspirantModule::class,
        ModifySupervisorModule::class],
    dependencies = [CoreComponent::class]
)
@Screen
interface AdministratorComponent {

    fun inject(aspirantListFragment: AspirantListFragment)

    fun inject(addAspirantFragment: AddAspirantFragment)

    fun inject(addAspirantSecondStepFragment: AddAspirantSecondStepFragment)

    fun inject(supervisorListFragment: SupervisorListFragment)

    fun inject(addSupervisorFragment: AddSupervisorFragment)
    fun inject(modifyAspirantFragment: ModifyAspirantFragment)

    fun inject(modifySupervisorFragment: ModifySupervisorFragment)

}