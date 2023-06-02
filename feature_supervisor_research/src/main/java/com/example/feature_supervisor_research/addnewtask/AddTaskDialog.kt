package com.example.feature_supervisor_research.addnewtask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.feature_supervisor_research.R
import com.example.feature_supervisor_research.databinding.AddTaskDialogBinding
import com.example.feature_supervisor_research.databinding.ChangeGradeDialogBinding
import com.example.feature_supervisor_research.formatToString
import java.text.SimpleDateFormat
import java.util.*

class AddTaskDialog(private val addTaskListener: (String, Date) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val binding = AddTaskDialogBinding.inflate(layoutInflater)
        dialogBuilder.setView(binding.root)

        var selectedDate = Date(0)

        binding.cancelTaskTextView.setOnClickListener {
            dismiss()
        }

        binding.addTaskTextView.setOnClickListener {
            addTaskListener.invoke(binding.addNewTaskEditText.text.toString(), selectedDate)
            dismiss()
        }

        binding.choseDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                it.context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Add 1 to month because months are indexed from 0
                    selectedDate = GregorianCalendar(selectedYear, selectedMonth + 1, selectedDayOfMonth).time
                    binding.choseDateEditText.setText(selectedDate.formatToString())
                    Log.d("TTT","${selectedDate.formatToString()}")
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        return dialogBuilder.create()
    }


}