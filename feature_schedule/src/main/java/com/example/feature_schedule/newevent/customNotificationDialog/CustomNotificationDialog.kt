package com.example.feature_schedule.newevent.customNotificationDialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.postgraduate.cabinet.ui.R
import com.postgraduate.cabinet.feature_schedule.databinding.CustomNotificationDialogBinding

class CustomNotificationDialog(
    private val listener: CustomNotificationInterface
) : DialogFragment() {

    var checkedPosition = 0
    var inputTime: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customNotificationDialog = AlertDialog.Builder(requireContext())
        val customNotificationDialogBinding =
            CustomNotificationDialogBinding.inflate(layoutInflater)

        val customNotifications = resources.getStringArray(R.array.custom_notifications)

        customNotificationDialogBinding.editTextCustomNotification.setText(inputTime)

        when (checkedPosition) {
            0 -> customNotificationDialogBinding.radioButtonMinutes.isChecked = true
            1 -> customNotificationDialogBinding.radioButtonHours.isChecked = true
            2 -> customNotificationDialogBinding.radioButtonDays.isChecked = true
        }

        customNotificationDialogBinding.radioGroupCustomNotification.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                customNotificationDialogBinding.radioButtonMinutes.id -> checkedPosition = 0
                customNotificationDialogBinding.radioButtonHours.id -> checkedPosition = 1
                customNotificationDialogBinding.radioButtonDays.id -> checkedPosition = 2
            }
        }

        customNotificationDialogBinding.buttonCustomNotification.setOnClickListener {
            inputTime = customNotificationDialogBinding.editTextCustomNotification.text.toString()
            listener.onCustomNotificationSelected(
                checkedPosition,
                customNotifications[checkedPosition],
                inputTime
            )
        }

        customNotificationDialog.setView(customNotificationDialogBinding.root)

        return customNotificationDialog.create()
    }
}
