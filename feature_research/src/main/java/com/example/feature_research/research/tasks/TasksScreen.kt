package com.example.feature_research.research.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.model.Research
import com.example.core.model.Task
import com.example.core.utils.DateFormatter.dateFormat
import com.example.feature_research.research.ResearchViewModel
import com.postgraduate.cabinet.ui.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TasksScreen(
    researchViewModel: ResearchViewModel,
) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ) {
            val research = researchViewModel.listOfResearch.collectAsStateWithLifecycle()

            Card(elevation = 4.dp, modifier = Modifier.padding(8.dp)) {
                LazyColumn {
                    items(research.value.listOfTasks.sortedBy { it.date }) { task ->
                        TaskItem(
                            task = task,
                            researchViewModel = researchViewModel,
                            research = research.value,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
