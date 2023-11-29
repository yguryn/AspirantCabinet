package com.example.feature_research.research.research

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.model.Research
import com.example.feature_research.research.ResearchViewModel

@Composable
fun ResearchScreen(
    navController: NavController,
    researchViewModel: ResearchViewModel,
    semester: Int
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {
        researchViewModel.getAllResearches()
    }

    val research = researchViewModel.listOfResearch.collectAsStateWithLifecycle()
    val researchHere: Research = research.value
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.verticalScroll(state = scrollState)
        ) {
            ArticleCard(research, semester, {
                researchHere.listOfArticles.add(it)
                researchViewModel.updateResearch(researchHere)
            }) {
                researchHere.listOfArticles.remove(it)
                researchViewModel.updateResearch(researchHere)
            }
        }
    }
}
