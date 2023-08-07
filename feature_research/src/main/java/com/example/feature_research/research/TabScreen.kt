package com.example.feature_research.research

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.feature_research.addresearch.AddResearchScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen(
    navController: NavController,
    researchViewModel: ResearchViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val tabs = listOf("Роботи", "Завдання", "Інд. план")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = pagerState.currentPage, containerColor = Color.White) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                )
            }
        }
        HorizontalPager(
            pageCount = tabs.size,
            state = pagerState,
            modifier = Modifier.background(Color.Gray)
        ) { page ->
            when (page) {
                0 -> ResearchScreen(navController, researchViewModel, 1)
                1 -> TasksScreen(researchViewModel = researchViewModel)
                2 -> IndPlanScreen(researchViewModel = researchViewModel)
            }
        }
    }
}
