package com.example.feature_schedule.newevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Event
import com.example.feature_schedule.di.DaggerFragmentsComponent
import com.example.feature_schedule.schedule.ScheduleFragment.Companion.MINUTES_IN_HOUR
import com.example.feature_schedule.schedule.model.SelectedDate
import com.example.feature_schedule.utils.*
import com.postgraduate.cabinet.ui.R
import com.postgraduate.cabinet.feature_schedule.databinding.FragmentNewEventBinding
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import javax.inject.Inject

class NewEventFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentNewEventBinding

    private var hoursApplyEnd = getFormattedHours()
    private var minutesApplyEnd = getFormattedMinutes()

    private var hoursApplyStart = getFormattedHours()
    private var minutesApplyStart = getFormattedMinutes()

    private var calendarStartTime = Calendar.getInstance()
    private var calendarEndTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, get(Calendar.HOUR_OF_DAY) + 1)
    }
    private var eventStartTime = 0
    private var eventEndTime = 0
    private var selectedDate = SelectedDate(24, 2, 2023)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<NewEventViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSaveNewEvent.setOnClickListener {
            val event = Event(
                user_id = 1,
                title = binding.editTextTitle.text.toString(),
                description = binding.editTextDescription.text.toString(),
                event_start = calendarStartTime.time,
                event_end = calendarEndTime.time
            )
            Log.d("TTT", "${calendarStartTime.time}")
            Log.d("TTT", "${calendarEndTime.time}")
            viewModel.addEvent(event)
            findNavController().popBackStack()
        }
        viewModel.deleteEvent()

        binding.textViewTimeEnd.setOnClickListener {
            val endTimeParts = binding.textViewTimeEnd.text.split(":")
            val hours = endTimeParts.component1().toInt()
            val minutes = endTimeParts.component2().toInt()

            val timePicker = TimePickerDialog(
                requireContext(),
                R.style.AppTheme_MaterialTimePickerTheme,
                timePickerDialogListenerEnd,
                hours,
                minutes,
                true
            )

            timePicker.show()
        }

        binding.textViewDateStart.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.MyDatePickerStyle,
                this,
                selectedDate.currentYear,
                selectedDate.currentMonth,
                selectedDate.currentDay
            )

            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        binding.textViewTimeStart.setOnClickListener {
            val startTimeParts = binding.textViewTimeStart.text.split(":")
            val hours = startTimeParts.component1().toInt()
            val minutes = startTimeParts.component2().toInt()

            val timePicker = TimePickerDialog(
                requireContext(),
                R.style.AppTheme_MaterialTimePickerTheme,
                timePickerDialogListenerStart,
                hours,
                minutes,
                true
            )

            timePicker.show()
        }

        binding.imageViewModifyCross.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val timePickerDialogListenerStart: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
            val roundedTime = getRoundedTime(hours, minutes)
            val roundedHours = roundedTime.hours()
            val roundedMinutes = roundedTime.minutes()

            hoursApplyStart = roundedHours
            minutesApplyStart = roundedMinutes

            calendarStartTime.apply {
                set(Calendar.HOUR_OF_DAY, roundedHours)
                set(Calendar.MINUTE, roundedMinutes)
            }

            val formattedHours = resources.getString(R.string.formatted_time, roundedHours)
            val formattedMinutes = resources.getString(R.string.formatted_time, roundedMinutes)

            binding.textViewTimeStart.text = resources.getString(
                R.string.hours_minutes,
                formattedHours,
                formattedMinutes
            )

            eventStartTime = roundedHours * MINUTES_IN_HOUR + roundedMinutes

            checkTimeErrors()
        }

    private val timePickerDialogListenerEnd: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
            val roundedTime = getRoundedTime(hours, minutes)
            val roundedHours = roundedTime.hours()
            val roundedMinutes = roundedTime.minutes()

            hoursApplyEnd = roundedHours
            minutesApplyEnd = roundedMinutes

            calendarEndTime.apply {
                set(Calendar.HOUR_OF_DAY, roundedHours)
                set(Calendar.MINUTE, roundedMinutes)
            }

            val formattedHours = resources.getString(R.string.formatted_time, roundedHours)
            val formattedMinutes = resources.getString(R.string.formatted_time, roundedMinutes)

            binding.textViewTimeEnd.text = resources.getString(
                R.string.hours_minutes,
                formattedHours,
                formattedMinutes
            )

            eventEndTime = roundedHours * MINUTES_IN_HOUR + roundedMinutes

            checkTimeErrors()
        }

    fun checkTimeErrors() {

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendarStartTime.setDate(year, month, dayOfMonth)
        calendarEndTime.setDate(year, month, dayOfMonth)

        binding.textViewDateStart.text = calendarStartTime.timeInMillis.formatDateToPresent()
        binding.textViewDateEnd.text = calendarEndTime.timeInMillis.formatDateToPresent()

        checkTimeErrors()
    }

    private fun initDagger() {
        DaggerFragmentsComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}