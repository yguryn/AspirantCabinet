package com.example.feature_administrator.aspirantlist.listOfAspirants

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
import com.example.core.model.Aspirant
import com.example.feature_administrator.R
import com.example.feature_administrator.aspirantlist.di.DaggerAdministratorComponent
import com.example.feature_administrator.aspirantlist.listOfAspirants.recycler.AspirantAdapter
import com.example.feature_administrator.databinding.FragmentAspirantListBinding
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AspirantListFragment : Fragment() {

    private lateinit var binding: FragmentAspirantListBinding
    private lateinit var aspirantAdapter: AspirantAdapter

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
        binding = FragmentAspirantListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutAdminImageView.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().popBackStack()
        }

        aspirantAdapter = AspirantAdapter() {
            val bundle = Bundle().apply {
                putParcelable("aspirant", it)
            }
            findNavController().navigate(R.id.action_aspirantList_to_modify, bundle)
        }
        binding.aspirantRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.aspirantRecyclerView.adapter = aspirantAdapter

        viewModel.aspirants.observe(viewLifecycleOwner) {
            aspirantAdapter.listOfAspirants.submitList(it)
        }
        viewModel.getAllAspirants()

        binding.addAspirantImageView.setOnClickListener {
            findNavController().navigate(R.id.action_aspirantList_to_addAspirant)
        }
    }

    private fun initDagger() {
        DaggerAdministratorComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}