package com.example.feature_supervisor_research.aspirantlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Aspirant
import com.example.feature_supervisor_research.R
import com.example.feature_supervisor_research.aspirantlist.recycler.AspirantListAdapter
import com.example.feature_supervisor_research.databinding.FragmentSupervisorAspirantListBinding
import com.example.feature_supervisor_research.di.DaggerFragmentsComponent
import javax.inject.Inject

class AspirantListFragment : Fragment() {

    private lateinit var binding: FragmentSupervisorAspirantListBinding
    private lateinit var aspirantListAdapter: AspirantListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AspirantListViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupervisorAspirantListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeAspirants()
    }

    private fun setupRecyclerView() {
        binding.aspirantRecycler.layoutManager = LinearLayoutManager(requireContext())
        aspirantListAdapter = AspirantListAdapter(
            onAspirantClick = { aspirant -> navigateToAspirantDetails(aspirant) },
            onNewTaskClick = { aspirant -> navigateToAddNewTask(aspirant) },
            onIndPlanClick = { aspirant -> navigateToIndPlanList(aspirant) },
            requireContext()
        )
        binding.aspirantRecycler.adapter = aspirantListAdapter
    }

    private fun observeAspirants() {
        viewModel.check()
        viewModel.aspirant.observe(viewLifecycleOwner) { aspirants ->
            aspirantListAdapter.listOfAspirants.submitList(aspirants)
        }
    }

    private fun navigateTo(destinationId: Int, aspirant: Aspirant) {
        val args = Bundle().apply {
            putString("researchId", aspirant.researchId)
            putString("aspirantId", aspirant.id)
            putString("aspirantName", "${aspirant.surname} ${aspirant.name}")
        }
        findNavController().navigate(destinationId, args)
    }

    private fun navigateToAspirantDetails(aspirant: Aspirant) {
        navigateTo(R.id.action_aspirantList_to_aspirantDetails, aspirant)
    }

    private fun navigateToAddNewTask(aspirant: Aspirant) {
        navigateTo(R.id.action_aspirantList_to_addNewTask, aspirant)
    }

    private fun navigateToIndPlanList(aspirant: Aspirant) {
        navigateTo(R.id.action_aspirantList_to_indPlanList, aspirant)
    }

    private fun initDagger() {
        DaggerFragmentsComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}
