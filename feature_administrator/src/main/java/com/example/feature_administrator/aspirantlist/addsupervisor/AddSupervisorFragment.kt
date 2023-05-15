package com.example.feature_administrator.aspirantlist.addsupervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Supervisor
import com.example.feature_administrator.R
import com.example.feature_administrator.aspirantlist.addaspirant.AddAspirantViewModel
import com.example.feature_administrator.aspirantlist.di.DaggerAdministratorComponent
import com.example.feature_administrator.databinding.FragmentAddSupervisorBinding
import javax.inject.Inject

class AddSupervisorFragment : Fragment(R.layout.fragment_add_supervisor) {

    private lateinit var binding: FragmentAddSupervisorBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AddSupervisorViewModel> { viewModelFactory }

    private var supervisor = Supervisor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSupervisorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSupervisorButton.setOnClickListener {
            setSupervisorValues()
            viewModel.addSupervisor(supervisor)
        }
    }

    private fun setSupervisorValues() {
        binding.apply {
            supervisor.faculty = facultyEditText.text.toString()
            supervisor.name = nameEditText.text.toString()
            supervisor.surname = surnameEditText.text.toString()
            supervisor.middleName = middleNameEditText.text.toString()
            supervisor.email = facultyEditText.text.toString()
            supervisor.phone = facultyEditText.text.toString()
        }
    }

    private fun initDagger() {
        DaggerAdministratorComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }

}