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
import com.example.core.utils.SharedPreferencesHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.postgraduate.cabinet.di.DaggerAppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var prefsHelper: SharedPreferencesHelper

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
            if (prefsHelper.getString("USER_TYPE") == "Aspirant") {
                bottomNavigationView.menu.removeItem(R.id.go_to_administrator)
                Log.d("TTT", "tet")
                bottomNavigationView.menu.removeItem(R.id.go_to_administrator_supervisor)
            }

            if(prefsHelper.getString("USER_TYPE") == "Supervisor") {
                bottomNavigationView.menu.removeItem(R.id.go_to_administrator)
                bottomNavigationView.menu.removeItem(R.id.go_to_administrator_supervisor)
            }
            if (prefsHelper.getString("USER_TYPE") == "Administrator") {
                Log.d("TTT", "tet2")
                bottomNavigationView.menu.removeItem(R.id.go_to_research)
                bottomNavigationView.menu.removeItem(R.id.go_to_profile)
                bottomNavigationView.menu.removeItem(R.id.go_to_schedule)
            }
            if (destination.id == com.postgraduate.cabinet.feature_profile.R.id.profileFragment ||
                destination.id == com.postgraduate.cabinet.feature_schedule.R.id.scheduleFragment ||
                destination.id == com.example.feature_research.R.id.researchFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == com.postgraduate.cabinet.feature_login.R.id.registrationFragment ||
                destination.id == com.example.feature_administrator.R.id.aspirantList ||
                destination.id == com.example.feature_administrator.R.id.supervisorList ||
                destination.id == com.postgraduate.cabinet.feature_login.R.id.registrationFragment ||
                destination.id == com.example.feature_supervisor_research.R.id.aspirantListFragment
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
                com.example.feature_research.R.id.research_navigation,
                com.example.feature_administrator.R.id.administrator_navigation
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