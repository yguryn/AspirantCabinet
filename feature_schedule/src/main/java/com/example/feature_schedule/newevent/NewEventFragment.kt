package com.example.feature_schedule.newevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Event
import com.example.feature_schedule.di.DaggerFragmentsComponent
import com.example.feature_schedule.newevent.customNotificationDialog.CustomNotificationDialog
import com.example.feature_schedule.newevent.customNotificationDialog.CustomNotificationInterface
import com.example.feature_schedule.schedule.ScheduleFragment.Companion.MINUTES_IN_HOUR
import com.example.feature_schedule.schedule.model.SelectedDate
import com.example.feature_schedule.utils.*
import com.example.feature_schedule.utils.Constants.DATE_PATTERN
import com.example.feature_schedule.utils.Constants.NOTIFICATION_CUSTOM
import com.example.feature_schedule.utils.Constants.NOTIFICATION_NEVER
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.postgraduate.cabinet.ui.R
import com.postgraduate.cabinet.feature_schedule.databinding.FragmentNewEventBinding
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewEventFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    CustomNotificationInterface {

    private lateinit var binding: FragmentNewEventBinding

    private var hoursApplyEnd = getFormattedHours()
    private var minutesApplyEnd = getFormattedMinutes()

    private var hoursApplyStart = getFormattedHours()
    private var minutesApplyStart = getFormattedMinutes()

    private var calendarStartTime = Calendar.getInstance()
    private var calendarEndTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, get(Calendar.HOUR_OF_DAY) + 1)
    }
    private var notificationDelay: String = "Never"
    private var eventStartTime = 0
    private var eventEndTime = 0
    private var selectedDate = SelectedDate(24, 2, 2023)
    private var customNotificationIndex = 0

    private var editTextCustomNotification = ""

    private lateinit var customNotificationDialog: CustomNotificationDialog

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
            val dayFromTextView =
                binding.textViewDateStart.text.filter(Char::isDigit).toString().toInt()
            val calendarForDay = getCalendarForDay(dayFromTextView)
            val eventTimeStart = binding.textViewTimeStart.text.toString()
            val eventTimeEnd = binding.textViewTimeEnd.text.toString()
            val eventDateStart = getStringDate(calendarForDay.timeInMillis)
            val timeStartCalendar = timeToCalendar(eventTimeStart, eventDateStart)
            val event = Event(
                user_id = viewModel.getUserId()!!,
                title = binding.editTextTitle.text.toString(),
                description = binding.editTextDescription.text.toString(),
                event_start = calendarStartTime.time,
                event_end = calendarEndTime.time,
            )
            Log.d("TTT", "${calendarStartTime.time}")
            Log.d("TTT", "${calendarEndTime.time}")
            viewModel.addEvent(event)
            findNavController().popBackStack()
        }

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

    private fun showNotificationTimeDialog() {
        val notifications = resources.getStringArray(R.array.notifications)
        var checkedItemId = notifications.indexOf(binding.textViewNotificationAlarm.text)
        if (checkedItemId == -1) checkedItemId = NOTIFICATION_CUSTOM
        val builder = MaterialAlertDialogBuilder(requireContext())

        with(builder) {
            setSingleChoiceItems(notifications, checkedItemId) { dialog, notificationId ->
                if (notificationId != NOTIFICATION_CUSTOM) {
                    binding.textViewNotificationAlarm.text = notifications[notificationId]
                    notificationDelay = notifications[notificationId]
                }

                when (notificationId) {
                    NOTIFICATION_NEVER -> {
                        setAlarmVisibility(View.GONE)
                        customNotificationIndex = 0
                        editTextCustomNotification = ""
                    }

                    NOTIFICATION_CUSTOM -> {
                        showCustomNotificationTimeDialog()
                        if (notificationDelay == context.resources.getString(R.string.custom)) {
                            binding.textViewNotificationAlarm.text = notifications[0]
                            notificationDelay = notifications[0]
                        }

                        dialog.cancel()
                    }

                    else -> {
                        setAlarmVisibility(View.VISIBLE)
                        customNotificationIndex = 0
                        editTextCustomNotification = ""
                    }
                }
                dialog.dismiss()
            }
            show()
        }
    }

    private fun setAlarmVisibility(visibilityId: Int) {
        binding.textViewAlarmNotification.visibility = visibilityId
        binding.switchAlarm.visibility = visibilityId
        binding.textViewAlarmInfo.visibility = visibilityId
    }

    private fun showCustomNotificationTimeDialog() {
        customNotificationDialog = CustomNotificationDialog(this)
        customNotificationDialog.inputTime = editTextCustomNotification
        customNotificationDialog.checkedPosition = customNotificationIndex
        customNotificationDialog.show(childFragmentManager, null)
    }

    private fun getCalendarForDay(day: Int): Calendar =
        Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, day) }

    private fun getStringDate(time: Long): String =
        SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(Date(time))

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

    override fun onCustomNotificationSelected(
        checkedPosition: Int,
        checkedTime: String,
        inputTime: String
    ) {
        customNotificationDialog.dismiss()

        if (inputTime.isEmpty()) {
            binding.textViewNotificationAlarm.text = resources.getString(R.string.never)
            notificationDelay = resources.getString(R.string.never)
            editTextCustomNotification = inputTime
            customNotificationIndex = 0
            setAlarmVisibility(View.GONE)
        } else {
            customNotificationIndex = checkedPosition
            editTextCustomNotification = inputTime
            binding.textViewNotificationAlarm.text =
                resources.getString(R.string.custom_notification_time, inputTime, checkedTime)
            notificationDelay = "$inputTime $checkedTime"
            setAlarmVisibility(View.VISIBLE)
        }
    }
}