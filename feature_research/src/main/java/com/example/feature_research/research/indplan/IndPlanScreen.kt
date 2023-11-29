package com.example.feature_research.research.indplan

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.model.IndividualPlan
import com.example.core.utils.DateFormatter.formatToString
import com.example.feature_research.research.ResearchViewModel
import com.example.feature_research.research.datepicker.DatePicker
import com.postgraduate.cabinet.ui.R
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IndPlanScreen(
    researchViewModel: ResearchViewModel,
) {

    var showDialog by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf(Date()) }
    var form by remember { mutableStateOf("") }
    var editIndex by remember { mutableStateOf(-1) }

    val researchListState = researchViewModel.listOfResearch.collectAsStateWithLifecycle()
    val currentResearch = researchListState.value
    val workList = currentResearch.listOfIndividualPlan


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog = true
                editIndex = -1
                content = ""
                deadline = Date()
                form = ""
            }, backgroundColor = colorResource(id = R.color.brand_yellow)) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                if (showDialog) {
                    CustomDialog(
                        title = if (editIndex >= 0) stringResource(id = R.string.edit_content_work) else stringResource(
                            id = R.string.add_content_work
                        ),
                        onConfirm = {
                            if (content.isNotEmpty() && form.isNotEmpty()) {
                                val updatedWork = IndividualPlan(content, deadline, form)
                                if (editIndex >= 0) {
                                    currentResearch.listOfIndividualPlan[editIndex] = updatedWork
                                } else {
                                    currentResearch.listOfIndividualPlan?.add(updatedWork)
                                }
                                currentResearch.let { researchViewModel.updateResearch(it) }
                                showDialog = false
                                researchViewModel.addEvent(content, deadline, form)
                            }
                        },
                        onCancel = {
                            if (editIndex >= 0) {
                                currentResearch.listOfIndividualPlan.removeAt(editIndex)
                                currentResearch.let { researchViewModel.updateResearch(it) }
                            }
                            showDialog = false
                        }, onDismiss = {
                            showDialog = false
                        },
                        content = {
                            Column {
                                OutlinedTextField(
                                    value = content,
                                    onValueChange = { content = it },
                                    label = { Text(stringResource(id = R.string.content_work)) },
                                    singleLine = false
                                )

                                DatePicker(
                                    initialDate = deadline,
                                    onDateChange = { deadline = it },
                                    label = { Text(stringResource(id = R.string.deadline)) },
                                )

                                OutlinedTextField(
                                    value = form,
                                    onValueChange = { form = it },
                                    label = { Text(stringResource(id = R.string.form_performance)) },
                                    singleLine = false
                                )
                            }
                        }
                    )
                }

                LazyColumn {
                    items(workList) { work ->
                        Card(modifier = Modifier.padding(8.dp), elevation = 8.dp) {
                            TextButton(
                                onClick = {
                                    showDialog = true
                                    editIndex = workList.indexOf(work)
                                    content = work.content
                                    deadline = work.deadline
                                    form = work.form
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "${stringResource(id = R.string.content_work)}: ${work.content}",
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start,
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "${stringResource(id = R.string.term)}: ${work.deadline.formatToString()}",
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Start
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "${stringResource(id = R.string.form)}: ${work.form}",
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
