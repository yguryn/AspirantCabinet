package com.example.feature_administrator.aspirantlist.supervisorlist

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
import com.example.feature_administrator.R
import com.example.feature_administrator.aspirantlist.di.DaggerAdministratorComponent
import com.example.feature_administrator.aspirantlist.supervisorlist.recycler.SupervisorAdapter
import com.example.feature_administrator.databinding.FragmentSupervisorListBinding
import javax.inject.Inject

class SupervisorListFragment : Fragment(R.layout.fragment_supervisor_list) {

    private lateinit var binding: FragmentSupervisorListBinding
    private lateinit var supervisorAdapter: SupervisorAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<SupervisorListViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupervisorListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        supervisorAdapter = SupervisorAdapter() {
            val bundle = Bundle().apply {
                putParcelable("supervisor", it)
            }
            findNavController().navigate(R.id.action_supervisorList_to_modifySupervisor, bundle)
        }
        binding.supervisorRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.supervisorRecyclerView.adapter = supervisorAdapter

        viewModel.supervisors.observe(viewLifecycleOwner) {
            supervisorAdapter.listOfAspirants.submitList(it)
        }
        binding.addSupervisorImageView.setOnClickListener {
            findNavController().navigate(R.id.action_supervisorList_to_addSupervisor)
        }
        viewModel.getAllSupervisors()
    }

    private fun initDagger() {
        DaggerAdministratorComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}