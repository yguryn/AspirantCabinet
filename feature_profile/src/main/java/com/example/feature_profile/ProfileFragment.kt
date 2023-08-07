package com.example.feature_profile

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
import com.example.feature_profile.di.DaggerProfileComponent
import com.google.firebase.auth.FirebaseAuth
import com.postgraduate.cabinet.feature_profile.R
import com.postgraduate.cabinet.feature_profile.databinding.FragmentProfileBinding
import java.text.SimpleDateFormat
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    val dateFormat = SimpleDateFormat("dd-MM-yyyy")

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
        viewModel.getUserInfo()
        viewModel.aspirant.observe(viewLifecycleOwner) {
            binding.fullNameTextView.text = "${it.surname} ${it.name}  ${it.middleName}"
            binding.phoneTextView.text = it.phone
            binding.emailTextView.text = it.email
            binding.facultyTextView.text = it.faculty
            binding.groupTextView.text = it.group
            binding.birthdayTextView.text = dateFormat.format(it.birthday)
            binding.markTextView.text = "Оцінка викладача: ${it.grade}"
            binding.paymentFormTextView.text = if(it.isBudget) "Форма оплати: Держзамовлення" else "Форма оплати: Контракт"
            binding.educationFormTextView.text = "Форма навчання: ${it.educationForm}"
            binding.diplomNumberFormTextView.text = "Номер диплому: ${it.diplomaNumber}"
            viewModel.getSupervisorById(it.supervisorId)
            viewModel.getResearchById(it.researchId)

        }
        viewModel.supervisor.observe(viewLifecycleOwner) { supervisor->
            Log.d("TTT","$supervisor")
            binding.superVisorTextView.text = "${supervisor.surname} ${supervisor.name}  ${supervisor.middleName}"
        }
        viewModel.research.observe(viewLifecycleOwner) { research->
            Log.d("TTT","$research")
            binding.numberOfWorksTextView.text = research.listOfArticles.size.toString()
            binding.researchTextView.text = "${research.objectResearch}"
        }
        binding.logoutImageView.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().popBackStack()
        }
    }

    private fun initDagger() {
        DaggerProfileComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}