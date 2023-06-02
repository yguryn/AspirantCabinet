package com.example.feature_events_list.feature_event_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.CoreInjectHelper
import com.example.feature_events_list.R
import com.example.feature_events_list.databinding.FragmentEventListBinding
import com.example.feature_events_list.di.DaggerEventListComponent
import com.example.feature_events_list.feature_event_list.recycler.EventListAdapter
import javax.inject.Inject

class EventListFragment : Fragment(R.layout.fragment_event_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<EventListViewModel> { viewModelFactory }
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var binding: FragmentEventListBinding

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
        eventListAdapter = EventListAdapter() {
            val request = NavDeepLinkRequest.Builder
                .fromUri("example://modifyevent/$it".toUri())
                .build()

            findNavController().navigate(request)
        }
        binding.upcomingEventsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.upcomingEventsRecyclerView.adapter = eventListAdapter
        viewModel.events.observe(viewLifecycleOwner) {
            eventListAdapter.listOfEvents.submitList(it)
        }
        viewModel.getUpcomingEventsById()
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