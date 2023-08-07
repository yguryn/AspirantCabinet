package com.example.feature_supervisor_research.aspirantlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.CoreInjectHelper
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
        binding.aspirantRecycler.layoutManager = LinearLayoutManager(requireContext())
        aspirantListAdapter = AspirantListAdapter({
            val args = Bundle().apply {
                putString("123", it.researchId)
                putString("name", "${it.surname} ${it.name}")
            }
            findNavController().navigate(R.id.action_aspirantList_to_aspirantDetails, args)
        }, {
            val args = Bundle().apply {
                putString("123", it.researchId)
                putString("aspirant_id", it.id)
                putString("name", "${it.surname} ${it.name}")
            }
            findNavController().navigate(R.id.action_aspirantList_to_addNewTask, args)
        }, {
          viewModel.updateAspirantGrade(it)
        }, {
            val args = Bundle().apply {
                putString("123", it.researchId)
                putString("aspirant_id", it.id)
                putString("name", "${it.surname} ${it.name}")
            }
            findNavController().navigate(R.id.action_aspirantList_to_indPlanList, args)
        }, childFragmentManager)
        binding.aspirantRecycler.adapter = aspirantListAdapter
        viewModel.check()
        viewModel.aspirant.observe(viewLifecycleOwner) {
            aspirantListAdapter.listOfAspirants.submitList(it)
            Log.d("TTT","asp $it")
        }
    }

    private fun initDagger() {
        DaggerFragmentsComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}