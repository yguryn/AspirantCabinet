package com.example.feature_administrator.modifyaspirant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Aspirant
import com.example.feature_administrator.aspirantlist.addaspirant.supervisorselector.SupervisorSelectorDialog
import com.example.feature_administrator.aspirantlist.di.DaggerAdministratorComponent
import com.example.feature_administrator.databinding.FragmentModifyAspirantBinding
import javax.inject.Inject

class ModifyAspirantFragment : Fragment() {


    private lateinit var binding: FragmentModifyAspirantBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ModifyAspirantViewModel> { viewModelFactory }

    private var aspirant = Aspirant()
    private lateinit var supervisorSelectorDialog: SupervisorSelectorDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModifyAspirantBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun initDagger() {
        DaggerAdministratorComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aspirant = arguments?.getParcelable("aspirant")!!
        viewModel.getSuperVisorById(aspirant.supervisorId)
        viewModel.getAllSupervisors()
        viewModel.supervisor.observe(viewLifecycleOwner) {
            binding.supervisorTextView.text = "${it.surname} ${it.name} ${it.middleName}"
        }
        binding.myScapeToolbar.title = "${aspirant.surname} ${aspirant.name}"
        binding.apply {
            nameEditText.setText(aspirant.name)
            surnameEditText.setText(aspirant.surname)
            middleNameEditText.setText(aspirant.middleName)
            phoneNumberEditText.setText(aspirant.phone)
            emailNumberEditText.setText(aspirant.email)
            facultyEditText.setText(aspirant.faculty)
            groupEditText.setText(aspirant.group)
            specializationEditText.setText(aspirant.specialization)

        }
        viewModel.supervisors.observe(viewLifecycleOwner) { supervisors ->
            binding.supervisorTextView.setOnClickListener {
                supervisorSelectorDialog = SupervisorSelectorDialog(
                    supervisors
                ) {
                    binding.supervisorTextView.text = "${it.surname} ${it.name} ${it.middleName}"
                    aspirant.supervisorId = it.id
                    supervisorSelectorDialog.dismiss()
                }
                supervisorSelectorDialog.show(childFragmentManager, null)
            }
        }
        binding.apply {
            modifyAspirantButton.setOnClickListener {
                aspirant.apply {
                    name = nameEditText.text.toString()
                    surname = surnameEditText.text.toString()
                    middleName = middleNameEditText.text.toString()
                    phone = phoneNumberEditText.text.toString()
                    email = emailNumberEditText.text.toString()
                    faculty = facultyEditText.text.toString()
                    group = groupEditText.text.toString()
                    specialization = specializationEditText.text.toString()
                }
                viewModel.modifyAspirant(aspirant)
                findNavController().popBackStack()
            }
        }

        binding.containerAspirantDelete.setOnClickListener {
            viewModel.deleteAspirant(aspirant.id)
        }
    }
}