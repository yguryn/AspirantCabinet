package com.postgraduate.cabinet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.utils.Constants.ADMINISTRATOR
import com.example.core.utils.Constants.ASPIRANT
import com.example.core.utils.Constants.SUPERVISOR
import com.example.core.utils.Constants.USER_TYPE
import com.example.core.utils.SharedPreferencesHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.postgraduate.cabinet.di.DaggerAppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var prefsHelper: SharedPreferencesHelper
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var currentDestination: NavDestination

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        val component =
            DaggerAppComponent.builder()
                .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext))
                .build()
        component.inject(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentDestination = destination
            val userType = prefsHelper.getString(USER_TYPE)
            if (navController.currentBackStackEntry?.destination?.id == com.postgraduate.cabinet.feature_login.R.id.loginFragment) {
                when (userType) {
                    ASPIRANT -> updateBottomNavMenu(
                        R.menu.bottom_nav_menu,
                        bottomNavigationView,
                        userType
                    )

                    SUPERVISOR -> updateBottomNavMenu(
                        R.menu.bottom_nav_supervisor_menu,
                        bottomNavigationView, userType
                    )

                    ADMINISTRATOR -> updateBottomNavMenu(
                        R.menu.bottom_nav_admin_menu,
                        bottomNavigationView, userType
                    )
                }
            }
            if (destination.id == com.postgraduate.cabinet.feature_profile.R.id.profileFragment ||
                destination.id == com.postgraduate.cabinet.feature_schedule.R.id.scheduleFragment ||
                destination.id == com.example.feature_research.R.id.researchFragment ||
                destination.id == com.example.feature_administrator.R.id.aspirantList ||
                destination.id == com.example.feature_events_list.R.id.eventListFragment ||
                destination.id == com.example.feature_administrator.R.id.supervisorList ||
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
                com.example.feature_administrator.R.id.administrator_navigation,
                com.example.feature_events_list.R.id.event_list_navigation
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

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (currentDestination.id == com.postgraduate.cabinet.feature_profile.R.id.profileFragment ||
                currentDestination.id == com.postgraduate.cabinet.feature_schedule.R.id.scheduleFragment ||
                currentDestination.id == com.example.feature_research.R.id.researchFragment ||
                currentDestination.id == com.example.feature_administrator.R.id.aspirantList ||
                currentDestination.id == com.example.feature_events_list.R.id.event_list_navigation ||
                currentDestination.id == com.example.feature_administrator.R.id.supervisorList ||
                currentDestination.id == com.example.feature_supervisor_research.R.id.aspirantListFragment
            ) {
                finish()
            } else {
                supportFragmentManager.popBackStack()
            }
        }
    }

    private fun updateBottomNavMenu(
        menuResId: Int,
        bottomNavigationView: BottomNavigationView,
        type: String
    ) {
        bottomNavigationView.menu.clear()
        bottomNavigationView.inflateMenu(menuResId)
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
