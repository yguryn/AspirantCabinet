package com.example.feature_research.research.datepicker

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core.utils.DateFormatter.formatToString
import java.util.Date

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
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
                    Text(stringResource(id = com.postgraduate.cabinet.ui.R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isDatePickerVisible = false }
                ) {
                    Text(stringResource(id = com.postgraduate.cabinet.ui.R.string.cancelEng))
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
