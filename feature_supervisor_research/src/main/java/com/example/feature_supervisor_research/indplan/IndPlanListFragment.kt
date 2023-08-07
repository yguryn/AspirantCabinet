package com.example.feature_supervisor_research.indplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Research
import com.example.feature_supervisor_research.aspirantlist.AspirantListViewModel
import com.example.feature_supervisor_research.databinding.FragmentIndPlanBinding
import com.example.feature_supervisor_research.di.DaggerFragmentsComponent
import com.example.feature_supervisor_research.indplan.recycler.IndPlanAdapter
import javax.inject.Inject

class IndPlanListFragment : Fragment() {

    private lateinit var adapter: IndPlanAdapter
    private lateinit var binding: FragmentIndPlanBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<IndPlanViewModel> { viewModelFactory }

    private lateinit var currentResearch: Research

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndPlanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val researchId = arguments?.getString("123")
        val studentName = arguments?.getString("name")

        adapter = IndPlanAdapter() {
            currentResearch.listOfIndividualPlan[it].isDone = true
            viewModel.updateResearch(currentResearch, researchId!!)
        }

        binding.toolbar.title = studentName

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel.research.observe(viewLifecycleOwner) { research ->
            currentResearch = research
            adapter.indPlanList.submitList(research.listOfIndividualPlan)
        }

        viewModel.getResearchByIdUseCase(researchId!!)
    }

    private fun initDagger() {
        DaggerFragmentsComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}