package com.example.feature_research.research

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.model.Event
import com.example.core.model.IndividualPlan
import com.postgraduate.cabinet.ui.R
import java.text.SimpleDateFormat
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
//        topBar = { AppBar() },
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
                        title = if (editIndex >= 0) "Редагувати зміст роботи" else "Додати зміст роботи",
                        onConfirm = {
                            if (content.isNotEmpty() && form.isNotEmpty()) {
                                val updatedWork = IndividualPlan(content, deadline, form)
                                if (editIndex >= 0 && currentResearch != null) {
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
                            if (editIndex >= 0 && currentResearch != null) {
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
                                    label = { Text("Зміст роботи") },
                                    singleLine = false
                                )

                                DatePicker(
                                    initialDate = deadline,
                                    onDateChange = { deadline = it },
                                    label = { Text("Термін виконання") },
                                )

                                OutlinedTextField(
                                    value = form,
                                    onValueChange = { form = it },
                                    label = { Text("Форма виконання") },
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
                                        text = "Зміст роботи: ${work.content}",
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start,
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "Термін: ${work.deadline.formatToString()}",
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Start
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "Форма: ${work.form}",
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

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text("Індивідуальний План", color = Color.Black)
        },
        backgroundColor = colorResource(id = R.color.white),
    )
}

@Composable
fun CustomDialog(
    title: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Column(content = content) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.brand_yellow))
            ) {
                Text("Зберегти")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.brand_yellow))
            ) {
                Text("Скасувати")
            }
        }
    )
}


fun Date.formatToString(): String {
    val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return format.format(this)
}

@Composable
fun DatePicker(
    initialDate: Date,
    onDateChange: (Date) -> Unit,
    label: @Composable () -> Unit,
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }

    val date = remember { mutableStateOf(initialDate.formatToString()) }

    OutlinedTextField(
        value = date.value,
        onValueChange = {},
        label = label,
        enabled = false,
        readOnly = true,
        modifier = Modifier.clickable { isDatePickerVisible = true }
    )

    if (isDatePickerVisible) {
        MyDatePicker(
            date = date.value,
            setNewDate = {
                val newDate = Date(it)
                onDateChange(newDate)
                date.value = newDate.formatToString()
                isDatePickerVisible = false
            }
        )
    }
}
