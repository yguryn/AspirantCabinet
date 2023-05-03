package com.example.feature_research.addresearch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.model.Research
import com.example.feature_research.research.ResearchViewModel


@Composable
fun AddResearchScreen(
    researchViewModel: ResearchViewModel
) {

    var topicValue by remember { mutableStateOf("") }
    var subjectValue by remember { mutableStateOf("") }
    var objectValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = topicValue,
            onValueChange = {
                topicValue = it
            },
            label = { Text("Topic") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = subjectValue,
            onValueChange = { subjectValue = it },
            label = { Text("Subject") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = objectValue,
            onValueChange = { objectValue = it },
            label = { Text("Object") },
            modifier = Modifier.fillMaxWidth()
        )

        SemesterSelection()

        Button(
            onClick = {
                researchViewModel.addEvent(
                    Research(
                        objectResearch = objectValue,
                        subjectResearch = subjectValue
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .align(Alignment.End)
        ) {
            Text("Save")
        }
    }

}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(it, modifier = Modifier.wrapContentWidth())
            }
        }
    }
}

@Composable
fun SemesterSelection() {
    val countryList = listOf(
        "1 semester",
        "2 semester",
        "2 semester",
        "2 semester",
        "2 semester",
        "2 semester",
        "2 semester",
        "2 semester",
        "2 semester",
        "2 semester",
        "2 semester",
    )
    val text = remember { mutableStateOf("") } // initial value
    val isOpen = remember { mutableStateOf(false) } // initial value
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = {
        text.value = it
    }
    Box {
        Column {
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text(text = "Semester") },
                modifier = Modifier.fillMaxWidth()
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = countryList,
                openCloseOfDropDownList,
                userSelectedString
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { isOpen.value = true }
                )
        )
    }
}

