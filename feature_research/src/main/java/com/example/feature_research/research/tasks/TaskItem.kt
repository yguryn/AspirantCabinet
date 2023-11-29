package com.example.feature_research.research.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.Research
import com.example.core.model.Task
import com.example.core.utils.DateFormatter
import com.example.feature_research.research.ResearchViewModel
import com.postgraduate.cabinet.ui.R

@Composable
fun TaskItem(
    task: Task,
    researchViewModel: ResearchViewModel,
    research: Research,
    modifier: Modifier = Modifier
) {
    Row(
        Modifier
            .padding(start = 8.dp, end = 8.dp)
            .then(modifier)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = task.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = DateFormatter.dateFormat.format(task.date), fontSize = 16.sp)
        }
        Switch(
            checked = task.isDone,
            onCheckedChange = { isChecked ->
                task.isDone = isChecked
                researchViewModel.updateResearch(research)
            },
            colors = SwitchDefaults.colors(checkedThumbColor = colorResource(id = R.color.brand_yellow))
        )
    }
}