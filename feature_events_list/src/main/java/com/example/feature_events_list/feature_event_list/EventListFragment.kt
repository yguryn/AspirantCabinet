package com.example.feature_events_list.feature_event_list

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.CoreInjectHelper
import com.example.core.utils.Constants.SUPERVISOR
import com.example.feature_events_list.R
import com.example.feature_events_list.databinding.FragmentEventListBinding
import com.example.feature_events_list.di.DaggerEventListComponent
import com.example.feature_events_list.feature_event_list.recycler.EventListAdapter
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class EventListFragment : Fragment(R.layout.fragment_event_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<EventListViewModel> { viewModelFactory }
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var binding: FragmentEventListBinding
    private var countOfDays = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        setupLogoutButton()
        setupEventListAdapter()
        setupMoreOptionsButton()
    }

    private fun setupLogoutButton() {
        binding.logoutSuperImageView.isVisible = viewModel.getUserType() == SUPERVISOR
        binding.logoutSuperImageView.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().popBackStack()
        }
    }

    private fun setupEventListAdapter() {
        eventListAdapter = EventListAdapter {
            navigateToModifyEvent(it)
        }
        binding.upcomingEventsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventListAdapter
        }
        viewModel.events.observe(viewLifecycleOwner) {
            eventListAdapter.listOfEvents.submitList(it)
        }
        viewModel.getUpcomingEventsById(countOfDays)
    }

    private fun navigateToModifyEvent(eventId: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri("example://modifyevent/$eventId".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun setupMoreOptionsButton() {
        binding.moreImageView.setOnClickListener { view ->
            createAndShowPopupMenu(view)
        }
    }

    private fun createAndShowPopupMenu(anchor: View) {
        val contextWrapper = ContextThemeWrapper(context, R.style.PopupMenuStyle)
        val popupMenu = PopupMenu(contextWrapper, anchor)
        popupMenu.menuInflater.inflate(R.menu.days_menu, popupMenu.menu)

        val days = arrayOf(com.postgraduate.cabinet.ui.R.array.event_days)
        val selectedDayIndex = days.indexOfFirst { it.toString().contains(countOfDays.toString()) }
        val selectedDay = if (selectedDayIndex != -1) selectedDayIndex else 0

        popupMenu.menu.getItem(selectedDay).isChecked = true

        popupMenu.setOnMenuItemClickListener { menuItem ->
            val selectedDay = menuItem.title.toString().replace(Regex("[^\\d]"), "").toInt()
            countOfDays = selectedDay
            viewModel.getUpcomingEventsById(countOfDays)
            true
        }
        popupMenu.show()
    }

    private fun initDagger() {
        DaggerEventListComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }

    companion object {
        const val EVENT_ID =
            "com/example/feature_schedule/schedule/ScheduleFragment.kt.event_id"
    }
}