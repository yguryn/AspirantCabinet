package com.example.feature_research.research.datepicker

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.core.utils.DateFormatter.formatToString
import java.util.Date

@Composable
fun DatePicker(
    initialDate: Date,
    onDateChange: (Date) -> Unit,
    label: @Composable () -> Unit,
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }

    val date = remember { mutableStateOf(initialDate.formatToString()) }

    androidx.compose.material.OutlinedTextField(
        value = date.value,
        onValueChange = {},
        label = label,
        enabled = false,
        readOnly = true,
        modifier = Modifier.clickable { isDatePickerVisible = true }
    )

    if (isDatePickerVisible) {
        MyDatePicker(
            setNewDate = {
                val newDate = Date(it)
                onDateChange(newDate)
                date.value = newDate.formatToString()
                isDatePickerVisible = false
            }
        )
    }
}