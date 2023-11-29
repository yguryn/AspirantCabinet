package com.example.feature_profile

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
import com.example.core.model.Research
import com.example.core.model.Supervisor
import com.example.core.utils.DateFormatter.dateFormat
import com.example.feature_profile.di.DaggerProfileComponent
import com.postgraduate.cabinet.ui.R
import com.postgraduate.cabinet.feature_profile.databinding.FragmentProfileBinding
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupLogoutListener()
    }

    private fun setupObservers() {
        viewModel.apply {
            getUserInfo()
            aspirant.observe(viewLifecycleOwner) { aspirant ->
                updateAspirantInfo(aspirant)
                getSupervisorById(aspirant.supervisorId)
                getResearchById(aspirant.researchId)
            }
            supervisor.observe(viewLifecycleOwner, ::updateSupervisorInfo)
            research.observe(viewLifecycleOwner, ::updateResearchInfo)
        }
    }

    private fun updateSupervisorInfo(supervisor: Supervisor) {
        binding.superVisorTextView.text =
            "${supervisor.surname} ${supervisor.name}  ${supervisor.middleName}"
    }

    private fun updateResearchInfo(research: Research) {
        binding.apply {
            numberOfWorksTextView.text = research.listOfArticles.size.toString()
            researchTextView.text = "${research.objectResearch}"
        }
    }

    private fun setupLogoutListener() {
        binding.logoutImageView.setOnClickListener {
            viewModel.logOut()
            findNavController().popBackStack()
        }
    }

    private fun updateAspirantInfo(aspirant: Aspirant) {
        with(binding) {
            fullNameTextView.text = "${aspirant.surname} ${aspirant.name}  ${aspirant.middleName}"
            phoneTextView.text = aspirant.phone
            facultyTextView.text = aspirant.faculty
            groupTextView.text = aspirant.group
            birthdayTextView.text = dateFormat.format(aspirant.birthday)
            markTextView.text =
                "${requireContext().getString(R.string.supervisor_assessment)}: ${aspirant.grade}"
            paymentFormTextView.text =
                if (aspirant.isBudget) "${requireContext().getString(R.string.form_of_payment)}: ${
                    requireContext().getString(
                        R.string.state_order
                    )
                }" else "${
                    requireContext().getString(
                        R.string.form_of_payment
                    )
                }: ${requireContext().getString(R.string.contract)}"
            educationFormTextView.text =
                "${requireContext().getString(R.string.form_of_education)}: ${aspirant.educationForm}"
            diplomNumberFormTextView.text =
                "${requireContext().getString(R.string.diploma_number)}: ${aspirant.diplomaNumber}"
        }
    }

    private fun initDagger() {
        DaggerProfileComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}
