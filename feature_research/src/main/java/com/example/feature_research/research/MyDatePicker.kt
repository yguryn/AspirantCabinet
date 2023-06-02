package com.example.feature_research.research

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    date: String,
    setNewDate: (Long) -> Unit
) {
    var isDatePickerVisible by rememberSaveable { mutableStateOf(value = true) }

    val datePickerState = rememberDatePickerState()
    if (isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        setNewDate(datePickerState.selectedDateMillis!!)
                        isDatePickerVisible = false
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isDatePickerVisible = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                title = null
            )
        }
    }
}