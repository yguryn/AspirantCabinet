package com.example.feature_research.research

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Research
import com.example.feature_research.addresearch.AddResearchScreen
import com.example.feature_research.di.DaggerResearchComponent
import javax.inject.Inject

class ResearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ResearchViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MessageCard(
                    findNavController()
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(
            "TTT", "view" +
                    "created"
        )
        viewModel.getAllResearches()
    }

    @Composable
    fun MessageCard(findNavController: NavController) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "research"
        ) {
            composable("login") {
                LoginScreen(navController, findNavController)
            }
            composable("research") {
                ResearchScreen(navController = navController, viewModel)
            }
            composable("addResearch") {
                AddResearchScreen(viewModel)
            }
        }

    }

    private fun initDagger() {
        DaggerResearchComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}

@Composable
fun LoginScreen(
    navController: NavController,
    navigator: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Screen", color = Color.Blue, modifier = Modifier.clickable {

            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://example.google.app/settings_fragment_two".toUri())
                .build()
            navigator.navigate(request)
        })
        Text("Go to Profile Screen")
    }
}