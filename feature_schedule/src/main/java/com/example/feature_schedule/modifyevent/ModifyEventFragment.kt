package com.example.feature_schedule.modifyevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.provider.CalendarContract.Instances.EVENT_ID
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Event
import com.example.feature_schedule.di.DaggerFragmentsComponent
import com.example.feature_schedule.newevent.NewEventViewModel
import com.example.feature_schedule.schedule.ScheduleFragment
import com.example.feature_schedule.utils.*
import com.postgraduate.cabinet.feature_schedule.R
import com.postgraduate.cabinet.feature_schedule.databinding.FragmentModifyEventBinding
import java.util.*
import javax.inject.Inject

class ModifyEventFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentModifyEventBinding
    private lateinit var event: Event

    private var hoursApplyEnd = getFormattedHours()
    private var minutesApplyEnd = getFormattedMinutes()

    private var hoursApplyStart = getFormattedHours()
    private var minutesApplyStart = getFormattedMinutes()

    private var eventStartTime = 0
    private var eventEndTime = 0

    private var calendarStartTime = Calendar.getInstance()
    private var calendarEndTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, get(Calendar.HOUR_OF_DAY) + 1)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ModifyEventViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModifyEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(ScheduleFragment.EVENT_ID)
        Log.d("TTT", "mod$id")
        viewModel.getEventById(id!!)
        binding.dateEndTextView.text = id
        viewModel.event.observe(viewLifecycleOwner) {
            event = it
            binding.apply {
                timeStartTextView.text = formatDateToTime(it.event_start)
                timeEndTextView.text = formatDateToTime(it.event_end)
                dateStartTextView.text = formatDateToWeek(it.event_start)
                dateEndTextView.text = formatDateToWeek(it.event_end)
                descriptionEditText.setText(it.description)
                titleModifyEditText.setText(it.title)
                Log.d("TTT", "${dateStartTextView.text}")
                Log.d("TTT", "${timeStartTextView.text}")
            }
        }

        binding.buttonModifyAppBar.setOnClickListener {
            viewModel.modifyEvent(
                Event(
                    id = id,
                    user_id = viewModel.getUserId()!!,
                    title = binding.titleModifyEditText.text.toString(),
                    description = binding.descriptionEditText.text.toString(),
                    event_start = convertStringToDate(
                        calendarStartTime,
                        binding.timeStartTextView.text.toString()
                    ),
                    event_end = convertStringToDate(
                        calendarEndTime,
                        binding.timeEndTextView.text.toString()
                    ),
                )
            )
            findNavController().popBackStack()
        }

        binding.timeEndTextView.setOnClickListener {
            val endTimeParts = binding.timeEndTextView.text.split(":")
            val hours = endTimeParts.component1().toInt()
            val minutes = endTimeParts.component2().toInt()

            val timePicker = TimePickerDialog(
                requireContext(),
                com.postgraduate.cabinet.ui.R.style.AppTheme_MaterialTimePickerTheme,
                timePickerDialogListenerEnd,
                hours,
                minutes,
                true
            )

            timePicker.show()
        }

        binding.dateStartTextView.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                com.postgraduate.cabinet.ui.R.style.MyDatePickerStyle,
                this,
                calendarStartTime.get(Calendar.YEAR),
                calendarStartTime.get(Calendar.MONTH),
                calendarStartTime.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        binding.timeStartTextView.setOnClickListener {
            val startTimeParts = binding.timeStartTextView.text.split(":")
            val hours = startTimeParts.component1().toInt()
            val minutes = startTimeParts.component2().toInt()

            val timePicker = TimePickerDialog(
                requireContext(),
                com.postgraduate.cabinet.ui.R.style.AppTheme_MaterialTimePickerTheme,
                timePickerDialogListenerStart,
                hours,
                minutes,
                true
            )

            timePicker.show()
        }
        binding.modifyCrossImageView.setOnClickListener {
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

            val formattedHours = resources.getString(
                com.postgraduate.cabinet.ui.R.string.formatted_time,
                roundedHours
            )
            val formattedMinutes = resources.getString(
                com.postgraduate.cabinet.ui.R.string.formatted_time,
                roundedMinutes
            )

            binding.timeStartTextView.text = resources.getString(
                com.postgraduate.cabinet.ui.R.string.hours_minutes,
                formattedHours,
                formattedMinutes
            )

            eventStartTime = roundedHours * ScheduleFragment.MINUTES_IN_HOUR + roundedMinutes

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

            val formattedHours = resources.getString(
                com.postgraduate.cabinet.ui.R.string.formatted_time,
                roundedHours
            )
            val formattedMinutes = resources.getString(
                com.postgraduate.cabinet.ui.R.string.formatted_time,
                roundedMinutes
            )

            binding.timeEndTextView.text = resources.getString(
                com.postgraduate.cabinet.ui.R.string.hours_minutes,
                formattedHours,
                formattedMinutes
            )

            eventEndTime = roundedHours * ScheduleFragment.MINUTES_IN_HOUR + roundedMinutes

            checkTimeErrors()
        }

    fun checkTimeErrors() {

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendarStartTime.setDate(year, month, dayOfMonth)
        calendarEndTime.setDate(year, month, dayOfMonth)

        binding.dateStartTextView.text = calendarStartTime.timeInMillis.formatDateToPresent()
        binding.dateEndTextView.text = calendarEndTime.timeInMillis.formatDateToPresent()

        checkTimeErrors()
    }

    private fun initDagger() {
        DaggerFragmentsComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}