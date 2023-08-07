package com.example.feature_administrator.aspirantlist.addaspirant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Aspirant
import com.example.core.model.Supervisor
import com.example.feature_administrator.R
import com.example.feature_administrator.aspirantlist.addaspirant.supervisorselector.SupervisorSelectorDialog
import com.example.feature_administrator.aspirantlist.di.DaggerAdministratorComponent
import com.example.feature_administrator.aspirantlist.listOfAspirants.AspirantListViewModel
import com.example.feature_administrator.databinding.FragmentAddAspirantStep2Binding
import javax.inject.Inject

class AddAspirantSecondStepFragment : Fragment() {

    private lateinit var binding: FragmentAddAspirantStep2Binding
    private lateinit var supervisorSelectorDialog: SupervisorSelectorDialog

    private var aspirant = Aspirant()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AddAspirantViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAspirantStep2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aspirant = arguments?.getParcelable<Aspirant>("aspirant")!!
        viewModel.getAllSupervisors()
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
        Log.d("TTT", "$id")

        binding.addButton.setOnClickListener {
            if (checkIsAllDataIsCorrect()) {
                binding.inputErrorTextView.isVisible = false
                setAspirantValues()
                viewModel.addAspirant(aspirant)
                findNavController().navigate(R.id.action_addAspirantSecondStep_to_aspirantList)
            } else {
                binding.inputErrorTextView.isVisible = true
            }
        }
    }

    private fun checkIsAllDataIsCorrect(): Boolean {
        binding.apply {
            return (facultyEditText.text!!.isNotEmpty() && groupEditText.text!!.isNotEmpty()
                    && specializationEditText.text!!.isNotEmpty() && paymentForm.text.isNotEmpty() &&
                    supervisorTextView.text != "Виберіть наукового керівника")
        }
    }

    private fun setAspirantValues() {
        binding.apply {
            aspirant.faculty = facultyEditText.text.toString()
            aspirant.group = groupEditText.text.toString()
            aspirant.specialization = specializationEditText.text.toString()
        }
    }

    private fun initDagger() {
        DaggerAdministratorComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}