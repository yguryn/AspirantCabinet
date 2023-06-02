package com.example.feature_schedule.schedule

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Event
import com.example.feature_schedule.di.DaggerFragmentsComponent
import com.example.feature_schedule.schedule.model.DayOfMonthUI
import com.example.feature_schedule.schedule.recycler.CalendarAdapter
import com.example.feature_schedule.schedule.view.HoursColumnView.Companion.TIMELINE_START_AT
import com.example.feature_schedule.utils.toPx
import com.example.feature_schedule.schedule.view.RoomBookingCalendarView
import com.postgraduate.cabinet.feature_schedule.R
import com.postgraduate.cabinet.feature_schedule.databinding.FragmentScheduleBinding
import com.postgraduate.cabinet.feature_schedule.databinding.SingleRoomEventBinding
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var selectedCal = Calendar.getInstance()
    private lateinit var adapterCalendar: CalendarAdapter
    private var events: List<Event> = emptyList()
    private var mDayOfMonthUI: DayOfMonthUI = DayOfMonthUI(
        0,
        Calendar.getInstance(Locale.ENGLISH).get(Calendar.DAY_OF_MONTH),
        ""
    )
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ScheduleViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        setOnClickListener()
        viewModel.getAllEvents(selectedCal)

        binding.monthTextView.text = sdf.format(selectedCal.time)

        setupCalendarAdapter()

        viewModel.events.observe(viewLifecycleOwner) {
            events = it
            drawEventsFilteredByRoom()
        }

        viewModel.daysOfMonth.observe(viewLifecycleOwner) {
            adapterCalendar.setDate(it.toList())
        }
        setOnScrollListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            previousWeek.setOnClickListener {
                handlePreviousButtonClick()
            }

            nextWeek.setOnClickListener {
                handleNextButtonClick()
            }

            monthTextView.setOnClickListener {
                handleClickOnCurrentMonth()
            }
            addEvent.setOnClickListener {
                findNavController().navigate(R.id.action_scheduleFragment_to_newEventFragment)
            }
        }
    }

    private fun handlePreviousButtonClick() {
        when {
            linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 7 -> {
                linearLayoutManager.scrollToPosition(
                    linearLayoutManager.findFirstCompletelyVisibleItemPosition() - 7
                )
            }
            linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 -> {
                selectedCal.set(Calendar.MONTH, selectedCal.get(Calendar.MONTH) - 1)
                viewModel.getDaysOfMonth(selectedCal.time, isCurrentMonth())
                binding.monthTextView.text = sdf.format(selectedCal.time)
                linearLayoutManager.scrollToPosition(
                    adapterCalendar.itemCount - 1
                )
            }
            else -> {
                linearLayoutManager.scrollToPosition(
                    0
                )
            }
        }
    }

    private fun handleNextButtonClick() {
        when {
            linearLayoutManager.findLastCompletelyVisibleItemPosition() < (adapterCalendar.itemCount - 7) -> {
                linearLayoutManager.scrollToPosition(
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() + 7
                )
            }
            linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapterCalendar.itemCount - 1 -> {
                selectedCal.add(Calendar.MONTH, 1)
                viewModel.getDaysOfMonth(selectedCal.time, isCurrentMonth())
                binding.monthTextView.text = sdf.format(selectedCal.time)
                linearLayoutManager.scrollToPosition(
                    0
                )
            }
            else -> {
                linearLayoutManager.scrollToPosition(
                    adapterCalendar.itemCount - 1
                )
            }
        }
    }

    private fun isCurrentMonth(): Boolean {
        return selectedCal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
    }


    private fun setOnScrollListener() {
        Handler(Looper.getMainLooper()).post {
            binding.scrollViewRoomBookingCalendar.scrollTo(0, (ROW_HEIGHT_DP * 2).toPx.toInt())
        }

        binding.scrollViewRoomBookingCalendar.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            binding.scrollViewHoursColumn.scrollTo(0, scrollY)
        }
        binding.scrollViewHoursColumn.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            binding.scrollViewRoomBookingCalendar.scrollTo(0, scrollY)
        }
        scrollDaysRecyclerViewToSelectedDay()
    }


    private fun scrollDaysRecyclerViewToSelectedDay() {
        linearLayoutManager.scrollToPositionWithOffset(
            selectedCal.get(Calendar.DAY_OF_MONTH) - 1,
            resources.displayMetrics.widthPixels / 3
        )
    }

    private fun handleClickOnCurrentMonth() {

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.MyDatePickerStyle,
            this,
            selectedCal.get(Calendar.YEAR),
            selectedCal.get(Calendar.MONTH),
            selectedCal.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun setupCalendarAdapter() {

        adapterCalendar = CalendarAdapter(object : CalendarAdapter.OnItemClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(dayOfMonthUI: DayOfMonthUI) {
                mDayOfMonthUI = dayOfMonthUI
                viewModel.modifyDaysOfMonth(dayOfMonthUI)

                val calendarForDay = Calendar.getInstance()
                selectedCal.set(Calendar.DAY_OF_MONTH, mDayOfMonthUI.day)


                calendarForDay.set(
                    selectedCal.get(Calendar.YEAR),
                    selectedCal.get(Calendar.MONTH),
                    mDayOfMonthUI.day
                )

                viewModel.getAllEvents(selectedCal)
            }
        })

        linearLayoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        binding.calendarRecycler.layoutManager = linearLayoutManager
        binding.calendarRecycler.adapter = adapterCalendar
    }

    private fun initDagger() {
        DaggerFragmentsComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }


    private fun formatMonthYear(it: Long): String {
        var formattedMonthYear = ""
        selectedCal.time = Date(it)
        formattedMonthYear += sdf.format(selectedCal.time)
        return formattedMonthYear
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun drawEventsFilteredByRoom() {
        binding.singleRoomTimeSlotsLayout.removeAllViews()

        events.filter { it.event_start != selectedCal.time }.forEach { event ->
            binding.singleRoomTimeSlotsLayout.drawEventOnSingleColumn(event)
        }
    }

    private fun showEventDetailsDialog(event: Event, roomColor: Int) {
        EventDetailsDialog(
            event,
            roomColor
        ).show(
            childFragmentManager,
            null
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun FrameLayout.drawEventOnSingleColumn(
        event: Event
    ) {
        val calendarStartTime = Calendar.getInstance()
        calendarStartTime.time = event.event_start
        val calendarEndTime = Calendar.getInstance()
        calendarEndTime.time = event.event_end

        val minutesFrom: Int = calendarStartTime.get(Calendar.MINUTE)
        val hourFrom: Int = calendarStartTime.get(Calendar.HOUR_OF_DAY)


        val minutesTo: Int = calendarEndTime.get(Calendar.MINUTE)
        val hourTo: Int = calendarEndTime.get(Calendar.HOUR_OF_DAY)

        val timeFrom = hourFrom.toFloat() + minutesFrom.toFloat() / MINUTES_IN_HOUR
        val timeTo = hourTo.toFloat() + minutesTo.toFloat() / MINUTES_IN_HOUR

        val singleRoomEventBinding = SingleRoomEventBinding.inflate(layoutInflater)
        val view = singleRoomEventBinding.root

        val roomColor = Color.parseColor("#F3AF00")
        var eventCreatorName = ""
        var eventCreatorPosition = ""

        singleRoomEventBinding.coloredEventMarker.setBackgroundColor(roomColor)



        val delta = timeTo - timeFrom

        when {
            delta >= 0.25f && delta < 0.5f -> {
                singleRoomEventBinding.eventTitle.isVisible = false
                singleRoomEventBinding.eventCreatorName.text = eventCreatorName
                singleRoomEventBinding.eventCreatorPosition.text = eventCreatorPosition
            }
            delta >= 0.5f && delta < 1f -> {
                singleRoomEventBinding.eventTitle.text = event.title
                singleRoomEventBinding.eventTitle.maxLines = 1
                singleRoomEventBinding.eventCreatorName.text = eventCreatorName
                singleRoomEventBinding.eventCreatorPosition.text = eventCreatorPosition
            }
            else -> {
                singleRoomEventBinding.eventTitle.text = event.title
                singleRoomEventBinding.eventCreatorName.text = eventCreatorName
                singleRoomEventBinding.eventCreatorPosition.text = eventCreatorPosition
            }
        }

        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            (RoomBookingCalendarView.CELL_SIZE_HEIGHT_PX * delta).toInt()
        )

        params.topMargin =
            (RoomBookingCalendarView.CELL_SIZE_HEIGHT_PX * (timeFrom - TIMELINE_START_AT) + RoomBookingCalendarView.PADDING_TOP_PX).toInt()

        singleRoomEventBinding.eventCardView.setOnClickListener {
            if (System.currentTimeMillis() < event.event_start.time) {
                val eventId: String = event.id
                val args = Bundle().apply {
                    putString(EVENT_ID, eventId)
                }
                Log.d("TTT", "qwe${event.id}")
                findNavController().navigate(
                    R.id.action_scheduleFragment_to_modifyEventFragment,
                    args
                )
            } else {
                showEventDetailsDialog(event, Color.parseColor("#F3AF00"))
            }
        }
        this.addView(view, params)
    }

    override fun onResume() {
        super.onResume()
        Log.d("TTT","BACK")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("TTT","BACK2")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selectedCal.run {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        val formattedMonthYearWithNumber: String = formatMonthYear(selectedCal.timeInMillis)

        val nameMonthOfYearSelect = SimpleDateFormat(
            "MMMM",
            Locale.getDefault()
        ).format(selectedCal.timeInMillis)

        val nameOfMonthCurrent = SimpleDateFormat(
            "MMMM",
            Locale.getDefault()
        ).format(Date())

        val selectDay = SimpleDateFormat(
            "d",
            Locale.getDefault()
        ).format(selectedCal.timeInMillis)

        if (nameMonthOfYearSelect.lowercase() == nameOfMonthCurrent.lowercase()) {
            viewModel.getDaysOfMonth(Date())
        } else {
            viewModel.getDaysOfMonth(Date(selectedCal.timeInMillis), currentMonth = false)
        }

        viewModel.modifyDaysOfMonth(numberOfMonth = selectDay.toInt())

        binding.monthTextView.text = formattedMonthYearWithNumber

        scrollDaysRecyclerViewToSelectedDay()
    }

    companion object {
        const val EVENT_ID =
            "com/example/feature_schedule/schedule/ScheduleFragment.kt.event_id"
        const val MINUTES_IN_HOUR = 60
        const val QUARTER_HOUR = 15
        const val ROW_HEIGHT_DP = 90
        val ONE_HOUR_GRID_CELL_IN_PX = RoomBookingCalendarView.CELL_SIZE_HEIGHT_PX.toInt()
    }
}