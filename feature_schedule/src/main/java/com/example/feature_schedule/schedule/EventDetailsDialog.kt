package com.example.feature_schedule.schedule

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.core.model.Event
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

        val format = SimpleDateFormat("yyyy-MM-dd")
        format.applyPattern("dd MMMM yyyy")


        binding.borderRedLineExp.setBackgroundColor(roomColor)
        binding.borderRedLine.setBackgroundColor(roomColor)
        binding.nameEvent.text = event.title
        binding.timeStart.text = getTimeString(event.event_start)
        binding.timeEnd.text = getTimeString(event.event_end)
        binding.dateOfBooking.text = format.format(event.event_start)
        binding.nameUser.text = "eventCreatorName"
        binding.emailUser.text = "event.user.email"
        binding.emailUser.setOnClickListener {
            val balloon = createBalloon(requireContext()) {
                setArrowSize(10)
                setWidth(80)
                setHeight(42)
                setCornerRadius(4f)
                setAlpha(0.9f)
                setText("copy")
                setTextSize(16f)
                setTextColor(Color.BLACK)
                setTextIsHtml(true)
                setBackgroundColor(Color.WHITE)
                setBalloonAnimation(BalloonAnimation.FADE)
                setLifecycleOwner(lifecycleOwner)
                setArrowOrientation(ArrowOrientation.BOTTOM)
                setDismissWhenClicked(true)
            }
            balloon.showAlignBottom(it)
        }
        binding.description.text = event.description
        binding.openCardView.isVisible = true

        alertDialog.setView(binding.root)

        return alertDialog.create()
    }
}