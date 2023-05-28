package com.example.feature_administrator.aspirantlist.modifysupervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Supervisor
import com.example.feature_administrator.aspirantlist.di.DaggerAdministratorComponent
import com.example.feature_administrator.databinding.FragmentModifySupervisorBinding
import javax.inject.Inject

class ModifySupervisorFragment : Fragment() {


    private lateinit var binding: FragmentModifySupervisorBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ModifySupervisorViewModel> { viewModelFactory }

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
        binding = FragmentModifySupervisorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supervisor = arguments?.getParcelable("supervisor")!!
        setViewValues()

        binding.aspirantListToolbar.title = "${supervisor.surname} ${supervisor.name} "

        binding.modifySupervisorButton.setOnClickListener {
            setSupervisorValues()
            viewModel.modifySupervisor(supervisor)
            findNavController().popBackStack()
        }

        binding.containerSupervisorDelete.setOnClickListener {
            viewModel.deleteSupervisor(supervisor.id)
            findNavController().popBackStack()
        }
    }

    private fun setViewValues() {
        binding.apply {
            facultyEditText.setText(supervisor.faculty)
            nameEditText.setText(supervisor.name)
            surnameEditText.setText(supervisor.surname)
            middleNameEditText.setText(supervisor.middleName)
            facultyEditText.setText(supervisor.email)
            facultyEditText.setText(supervisor.phone)
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