package com.example.feature_schedule.schedule

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.core.model.Event
import com.example.feature_schedule.utils.format
import com.example.feature_schedule.utils.getTimeString
import com.postgraduate.cabinet.feature_schedule.databinding.MySpaceHistoryItemBinding
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon
import java.text.SimpleDateFormat

class EventDetailsDialog(
    private val event: Event,
    private val roomColor: Int
) : DialogFragment() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(requireContext())

        val binding = MySpaceHistoryItemBinding.inflate(layoutInflater)

        binding.apply {
            borderRedLine.setBackgroundColor(roomColor)
            nameEvent.text = event.title
            timeStart.text = getTimeString(event.event_start)
            timeEnd.text = getTimeString(event.event_end)
            dateOfBooking.text = format.format(event.event_start)
            description.text = event.description
        }
        alertDialog.setView(binding.root)

        return alertDialog.create()
    }
}