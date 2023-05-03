package com.postgraduate.cabinet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.core.di.CoreInjectHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.postgraduate.cabinet.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val component =
            DaggerAppComponent.builder()
                .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext))
                .build()
        component.inject(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == com.postgraduate.cabinet.feature_profile.R.id.profileFragment ||
                destination.id == com.postgraduate.cabinet.feature_schedule.R.id.scheduleFragment ||
                destination.id == com.example.feature_research.R.id.researchFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == com.postgraduate.cabinet.feature_login.R.id.registrationFragment
            ) {
                showBottomNavigationView()

            } else {
                hideBottomNavigationView()
            }
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                com.postgraduate.cabinet.feature_schedule.R.id.schedule_navigation,
                com.postgraduate.cabinet.feature_profile.R.id.profile_navigation,
                com.example.feature_research.R.id.research_navigation
            )
        )
        supportActionBar?.hide()
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)

            return@setOnItemSelectedListener true
        }
    }

    private fun hideBottomNavigationView() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.isVisible = false
    }

    private fun showBottomNavigationView() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.isVisible = true
    }
}